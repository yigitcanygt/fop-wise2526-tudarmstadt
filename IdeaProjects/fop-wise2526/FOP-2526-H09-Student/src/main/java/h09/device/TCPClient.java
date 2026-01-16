package h09.device;

import h09.exceptions.InternetException;
import h09.exceptions.packet.PacketException;
import h09.utils.TCPUtils;
import h09.packet.Packet;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import static h09.packet.PacketType.*;

import java.util.concurrent.ThreadLocalRandom;

/**
 * A TCP client implementation that extends the abstract {@link Client} class.
 * Implements TCP-specific functionality for establishing connections,
 * sending and receiving data with proper sequencing and acknowledgment.
 */
@DoNotTouch
public class TCPClient extends Client {

    /**
     * The current sequence number for TCP communication.
     */
    private int sequence;

    public TCPClient(int serverPort) throws InternetException {
        super(serverPort);
        sequence = ThreadLocalRandom.current().nextInt(1, 1000);
    }

    /**
     * Connects to the remote server by sending a SYN packet and awaiting
     * a response SYN packet from the server. Also validates the returned
     * packet.
     *
     * @throws InternetException If there is an error with the network connection
     * @throws PacketException   If there is an error with packet handling
     */
    @Override

    @StudentImplementationRequired("H9.4.1")
    public void connect() throws InternetException, PacketException {
        final int startSequence = sequence;
        Packet fromServer = TCPUtils.try3Times(
            () -> {
                getConn().sendPacket(startSequence, SYN, null);
                return getConn().waitForPacketTimeout(5000);
            },
            startSequence + 1
        );
        fromServer.validateChecksum();
        fromServer.expectType(SYN);
        int serverSequence = fromServer.getHeader().sequenceNumber();
        int newSequence = serverSequence + 1;
        sequence = newSequence;
    }

    /**
     * Sends a {@link String} of variable, unbounded length to the remote server. Packs the
     * data into one or more DATA packets if necessary. For each DATA packet
     * also awaits the acknowledgement packet and validates it.
     * <p>
     * After all data is sent, a DATA packet with content {@code "<EOF>"} is sent
     * to signal the end of the client's data. The response ACK is also validated.
     *
     * @param data The data to send
     * @throws InternetException        If there is an error with the network connection
     * @throws PacketException          If there is an error with packet handling
     * @throws IllegalArgumentException if data is null
     */
    @Override
    @StudentImplementationRequired("H9.4.2")
    public void send(String data) throws InternetException, PacketException {
        if (data == null) {
            throw new IllegalArgumentException("Veri null olamaz");
        }
        int index = 0;
        int totalSize = data.length();
        while (index < totalSize) {
            int lastIndex = index + 8;
            if (lastIndex > totalSize) {
                lastIndex = totalSize;
            }
            final String dataPiece = data.substring(index, lastIndex);
            final int sequenceToBeSent = sequence;
            Packet ackPacket = TCPUtils.try3Times(
                () -> {
                    getConn().sendPacket(sequenceToBeSent, DATA, dataPiece);
                    return getConn().waitForPacketTimeout(5000);
                },
                sequenceToBeSent + 1
            );
            ackPacket.validateChecksum();
            ackPacket.expectType(ACK);
            int incomingAckSeq = ackPacket.getHeader().sequenceNumber();
            int nextSequence = incomingAckSeq + 1;
            sequence = nextSequence;
            index = lastIndex;
        }
        final int forEoF = sequence;
        Packet eofAck = TCPUtils.try3Times(
            () -> {
                getConn().sendPacket(forEoF, DATA, "<EOF>");
                return getConn().waitForPacketTimeout(5000);
            },
            forEoF + 1
        );
        eofAck.validateChecksum();
        eofAck.expectType(ACK);
        int eofAckSeq = eofAck.getHeader().sequenceNumber();
        sequence = eofAckSeq + 1;
    }

    /**
     * Retrieves the message from the remote server, combining multiple
     * DATA packets if necessary.
     * <p>
     * First sends an ACK to signal that the server can now send data.
     * Then awaits a DATA packet from the server, validates it and responds
     * with an acknowledgement. Repeats receiving and acknowledging data
     * until the server sends a DATA packet with {@code "<EOF>"}.
     * The EOF packet does not need to be acknowledged.
     *
     * @return the complete message from the server
     * @throws InternetException If there is an error with the network connection
     * @throws PacketException   If there is an error with packet handling
     */
    @Override
    @StudentImplementationRequired("H9.4.3")
    public String receive() throws InternetException, PacketException {
        StringBuilder collector = new StringBuilder();
        int firstACKSequence = sequence;
        getConn().sendPacket(firstACKSequence, ACK, null);
        int newSequence = firstACKSequence + 1;
        sequence = newSequence;
        boolean cont = true;
        while (cont == true) {
            int expectedSequence = sequence;
            Packet incomingData = TCPUtils.try3Times(
                () -> getConn().waitForPacketTimeout(5000),
                expectedSequence
            );
            incomingData.validateChecksum();
            incomingData.expectType(DATA);
            incomingData.expectSequenceNumber(expectedSequence);
            sequence = sequence + 1;
            String receivedData = incomingData.getData();
            if (receivedData.equals("<EOF>")) {
                cont = false;
            } else {
                collector.append(receivedData);
                int dataLength = receivedData.length();
                sequence = sequence + dataLength;
                int ackFileToSend = sequence;
                getConn().sendPacket(ackFileToSend, ACK, null);
                sequence = ackFileToSend + 1;
            }
        }
        String doneData = collector.toString();
        return doneData;
    }

    /**
     * Closes the connection. Sends a CLOSE packet to the
     * server with a sequence number of {@code Integer.MAX_VALUE}.
     * If the connection is already closed or another error occurs
     * while sending the packet, the error is caught and ignored.
     * At the end calls close on the superclass.
     */
    @Override
    @StudentImplementationRequired("H9.4.4")
    public void close() {
        try {
            getConn().sendPacket(Integer.MAX_VALUE, CLOSE, null);
        } catch (Exception e) {
        }
        super.close();
    }
}
