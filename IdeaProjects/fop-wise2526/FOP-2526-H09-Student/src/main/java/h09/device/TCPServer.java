package h09.device;

import h09.exceptions.TimeoutException;
import h09.exceptions.packet.PacketException;
import h09.packet.PacketType;
import h09.utils.TCPUtils;
import h09.connection.Connection;
import h09.exceptions.InternetException;
import h09.packet.Packet;
import h09.utils.Verbose;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import static h09.packet.PacketType.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadFactory;

/**
 * A TCP server implementation that extends the abstract {@link Server} class.
 * Handles TCP connections from clients, processes incoming packets,
 * and manages multiple client connections simultaneously.
 */
@DoNotTouch
public class TCPServer extends Server {

    private final Map<Integer, TCPHandler> sourcePortQueues;
    private final ThreadFactory threadFactory;

    /**
     * Constructs a TCP server that listens on the specified port.
     *
     * @param port The port number to listen on
     * @throws InternetException If there is an error registering the device
     */
    @DoNotTouch
    public TCPServer(int port) throws InternetException {
        super(port);
        this.sourcePortQueues = new HashMap<>();
        this.threadFactory = Thread.ofVirtual().name("tcp-server[" + getPort() + "]-", 0).factory();
    }

    /**
     * Handles incoming packets for this server.
     *
     * @param packet The packet received by this server
     */
    @Override
    @DoNotTouch
    public void accept(Packet packet) {
        Verbose.out.printf("tcp-server[%d]: received packet %s%n", getPort(), packet);
        int clientPort = packet.getHeader().sourcePort();
        if (packet.getHeader().type() == CLOSE) {
            sourcePortQueues.computeIfPresent(clientPort, (integer, tcpHandler) -> {
                tcpHandler.thread().interrupt();
                return null;
            });
            return;
        }
        if (!sourcePortQueues.containsKey(clientPort) && packet.getHeader().type() != SYN) {
            return;
        }
        sourcePortQueues.computeIfAbsent(clientPort, p -> {
            Verbose.out.printf("tcp-server[%d]: creating new handler for port %d%n", getPort(), p);
            Connection conn = new Connection(this.getPort(), p);
            Thread th = threadFactory.newThread(() -> {
                try {
                    handleTCPServer(conn);
                } catch (RuntimeException e) {
                    if (!(e.getCause() instanceof InterruptedException)) {
                        System.out.printf("Following RuntimeException was thrown in thread %s: %n",
                            Thread.currentThread().getName());
                        e.printStackTrace(System.out);
                    }
                } catch (Exception e) {
                    System.out.printf("Following %s was thrown in thread %s: %n",
                        e.getClass().getSimpleName(),
                        Thread.currentThread().getName());
                    e.printStackTrace(System.out);
                } finally {
                    // SAFETY: the first packet will be handled after the element gets created
                    // thus this will actually remove the element and not get called out of order
                    sourcePortQueues.remove(p);
                }
                Verbose.out.println(Thread.currentThread().getName() + ": finished");
            });
            th.start();
            return new TCPHandler(th, conn);
        }).conn().addPacket(packet);
    }

    /**
     * Handles the TCP server protocol for a single client connection.
     * Implements the TCP handshake, data exchange, and connection termination.
     *
     * @param conn The connection to the client
     * @throws InternetException If there is an error with the network connection
     * @throws PacketException   If there is an error with packet handling
     */
    @DoNotTouch
    private void handleTCPServer(Connection conn) throws InternetException, PacketException {
        Verbose.out.println(Thread.currentThread().getName() + ": handler started");
        Packet clientSyn = conn.waitForPacket(); // we are guaranteed to have a packet if we got here
        clientSyn.expectType(SYN);
        Verbose.out.println(Thread.currentThread().getName() + ": received SYN packet");
        IntHolder seq = new IntHolder(clientSyn.getHeader().sequenceNumber());
        Verbose.out.println(Thread.currentThread().getName() + ": sending response SYN packet");
        PacketType responseType = SYN;
        StringBuilder buffer = new StringBuilder();
        boolean requestFinished = false;

        do {
            // required because responseType is not _effectively final_
            PacketType finalResponseType = responseType;

            Packet moreData = TCPUtils.try3Times(() -> {
                conn.sendPacket(seq.value + 1, finalResponseType, null);
                return conn.waitForPacketTimeout(5000);
            }, seq.value + 2);
            moreData.expectType(DATA);
            moreData.expectSequenceNumber(seq.value += 2);
            moreData.validateChecksum();
            seq.value += moreData.getData().length();
            Verbose.out.println(Thread.currentThread().getName() + ": received a DATA Packet");

            String data = moreData.getData();
            if (data.equals("<EOF>")) {
                requestFinished = true;
            } else {
                buffer.append(data);
            }

            responseType = ACK;
        } while (!requestFinished);

        Packet rcvACK = TCPUtils.try3Times(() -> {
            conn.sendPacket(seq.value + 1, ACK, null);
            // waiting for receive
            return conn.waitForPacket();
        }, seq.value + 2);
        rcvACK.expectType(ACK);
        rcvACK.expectSequenceNumber(seq.value += 2);
        rcvACK.validateChecksum();

        String query = buffer.toString();
        String response = getResponse(query);

        for (int i = 0; i < response.length(); i += 8) {
            int end = Math.min(response.length(), i + 8);
            String currentData = response.substring(i, end);
            Packet clientAck = TCPUtils.try3Times(() -> {
                conn.sendPacket(seq.value + 1, DATA, currentData);
                return conn.waitForPacketTimeout(5000);
            }, seq.value + currentData.length() + 2);
            clientAck.expectType(ACK);
            clientAck.expectSequenceNumber(seq.value += currentData.length() + 2);
            Verbose.out.println(Thread.currentThread().getName() + ": received an ACK for data.");
        }
        conn.sendPacket(seq.value + 1, DATA, "<EOF>");

        /*
        Makes sure that the final eof is received. As it does not need to be ACKd.
        If an old ack is sent (timeout from client) resends the packet.
        If a CLOSE is received the server is stopped anyway. But then the client
        either stopped or finished properly.

        Uses waitForPacketTimeout s.t. the server does not wait forever if the client failed and does
        not send anything anymore.

        Does not count the amount of errors as transmission is done anyway thus we do not need to fail
        but rather just wait for client to receive or fail.
         */
        boolean eofReceived = false;
        while(!eofReceived) {
            try {
                Packet p = conn.waitForPacketTimeout(5000);
                if(p.getHeader().sequenceNumber() <= seq.value) {
                    Verbose.out.println(Thread.currentThread().getName() + ": resent <EOF> of response");
                    conn.sendPacket(seq.value + 1, DATA, "<EOF>");
                } else {
                    eofReceived = true;
                }
            } catch (TimeoutException ignored) {}
        }
        Verbose.out.println(Thread.currentThread().getName() + ": Sent and ACKd entire response.");
    }

    /**
     * Generates a response message based on the client's request.
     * Provides predefined responses for known messages.
     *
     * @param message The client's request message
     * @return The server's response message
     */
    @DoNotTouch
    private String getResponse(String message) {
        if (message.equals("Hello Server")) {
            return "Hello Client :)";
        } else {
            return "Sorry. I did not understand that.";
        }
    }

    /**
     * Closes the server and all active client connections.
     */
    @Override
    @DoNotTouch
    public void close() {
        for (TCPHandler openHandler : sourcePortQueues.values()) {
            openHandler.thread().interrupt();
        }
        super.close();
    }

    /**
     * Record that holds a thread and connection for a TCP client handler.
     * Used to track and manage active client connections.
     */
    @DoNotTouch
    private record TCPHandler(Thread thread, Connection conn) {
    }

    /**
     * Helper class that holds an integer value that can be modified.
     * Used to track sequence numbers in the TCP protocol implementation.
     */
    @DoNotTouch
    private static class IntHolder {

        public int value;

        public IntHolder(int value) {
            this.value = value;
        }
    }

}
