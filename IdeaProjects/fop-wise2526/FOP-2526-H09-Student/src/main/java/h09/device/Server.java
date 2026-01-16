package h09.device;

import h09.connection.InternetPool;
import h09.exceptions.InternetException;
import h09.utils.PacketConsumer;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Abstract base class for network servers.
 * Provides common functionality for listening on a port and handling client connections.
 */
@DoNotTouch
public abstract class Server implements PacketConsumer {

    /**
     * The port number this server is listening on.
     */
    @DoNotTouch
    private final int port;

    /**
     * Constructs a server that listens on a specified port.
     * Registers the server with the {@link InternetPool}.
     *
     * @param port The port number to listen on
     * @throws InternetException If there is an error registering the device
     */
    @DoNotTouch
    public Server(int port) throws InternetException {
        this.port = port;
        InternetPool.registerDevice(port, this);
    }

    /**
     * Gets the port number this server is listening on.
     *
     * @return The port number
     */
    @DoNotTouch
    public int getPort() {
        return port;
    }

    /**
     * Closes the server.
     * Unregisters the server from the InternetPool.
     */
    @DoNotTouch
    public void close() {
        InternetPool.unregisterDevice(port);
    }

}
