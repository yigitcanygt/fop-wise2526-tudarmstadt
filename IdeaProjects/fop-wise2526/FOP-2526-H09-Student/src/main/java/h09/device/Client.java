package h09.device;

import h09.connection.Connection;
import h09.connection.InternetPool;
import h09.exceptions.ConnectionClosedException;
import h09.exceptions.InternetException;
import h09.exceptions.packet.PacketException;
import h09.packet.Packet;
import h09.packet.PacketType;
import h09.utils.PacketConsumer;
import h09.utils.Verbose;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Abstract base class for network clients.
 * Provides common functionality for establishing connections, sending and receiving data,
 * and handling incoming packets.
 */
@DoNotTouch
public abstract class Client implements PacketConsumer {

    /**
     * The connection to the server.
     */
    @DoNotTouch
    private Connection conn;

    /**
     * The port number assigned to this client.
     */
    @DoNotTouch
    private final int myPort;

    /**
     * Constructs a client that connects to a specified server port.
     * Gets a free port for this client and registers it with the {@link InternetPool}.
     *
     * @param serverPort The port number of the server to connect to
     * @throws InternetException If there is an error getting a free port or registering the device
     * @see InternetPool
     */
    @DoNotTouch
    public Client(int serverPort) throws InternetException {
        this.myPort = InternetPool.getFreePort();
        this.conn = new Connection(myPort, serverPort);
        InternetPool.registerDevice(myPort, this);
    }

    /**
     * Handles incoming packets for this client.
     * Adds the packet to the connection's queue and handles CLOSE packets.
     *
     * @param packet The packet received by this client
     */
    @DoNotTouch
    @Override
    public void accept(Packet packet) {
        Verbose.out.printf("client[%d]: received %s%n", myPort, packet);
        // due to timeout we could receive duplicates after CLOSE
        if(conn != null)
            conn.addPacket(packet);
        if (packet.getHeader().type() == PacketType.CLOSE) {
            this.innerClose();
        }
    }

    /**
     * Establishes a connection to the server.
     *
     * @throws InternetException If there is an error with the network connection
     * @throws PacketException   If there is an error with packet handling
     */
    @DoNotTouch
    public abstract void connect() throws InternetException, PacketException;

    /**
     * Sends data to the server.
     *
     * @param data The data to send
     * @throws InternetException If there is an error with the network connection
     * @throws PacketException   If there is an error with packet handling
     */
    @DoNotTouch
    public abstract void send(String data) throws InternetException, PacketException;

    /**
     * Receives data from the server.
     *
     * @return The received data as a string
     * @throws InternetException If there is an error with the network connection
     * @throws PacketException   If there is an error with packet handling
     */
    @DoNotTouch
    public abstract String receive() throws InternetException, PacketException;

    /**
     * Internal method to close the connection.
     * Unregisters the client from the InternetPool and closes the connection.
     */
    @DoNotTouch
    protected void innerClose() {
        InternetPool.unregisterDevice(myPort);
        conn = null;
    }

    /**
     * Gets the connection object for this client.
     *
     * @return The connection object
     * @throws ConnectionClosedException If the connection has been closed
     */
    @DoNotTouch
    public Connection getConn() throws ConnectionClosedException {
        if (conn == null)
            throw new ConnectionClosedException();
        return conn;
    }

    /**
     * Closes the client connection.
     * This method should be called when the client is no longer needed.
     */
    @DoNotTouch
    public void close() {
        innerClose();
    }

}
