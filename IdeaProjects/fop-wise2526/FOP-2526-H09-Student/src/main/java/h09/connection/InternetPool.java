package h09.connection;

import h09.exceptions.port.NoFreePortException;
import h09.exceptions.port.PortAllocatedException;
import h09.exceptions.port.UnknownPortException;
import h09.packet.Packet;
import h09.utils.PacketConsumer;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.util.concurrent.*;

/**
 * A central manager for network connections and packet routing.
 * This class provides functionality for registering devices to ports,
 * sending packets between devices, and managing port allocation.
 */
@DoNotTouch
public class InternetPool {

    /**
     * The maximum number of ports available in the system.
     */
    @DoNotTouch
    public static final int MAX_PORT = 65536;

    /**
     * Map of port numbers to packet consumers (devices) registered to those ports.
     */
    @DoNotTouch
    private static final ConcurrentMap<Integer, PacketConsumer> ports = new ConcurrentHashMap<>();

    /**
     * The channel used for routing packets between devices.
     */
    @DoNotTouch
    private static Channel channel = new Channel();

    /**
     * Registers a device in form of a {@link PacketConsumer} to a specific port.
     *
     * @param port          The port number to register the device to
     * @param packetHandler The packet consumer that will handle packets for this port
     * @throws PortAllocatedException If the port is already allocated
     */
    @DoNotTouch
    public static void registerDevice(int port, PacketConsumer packetHandler) throws PortAllocatedException {
        assert 0 <= port && port < MAX_PORT;
        assert packetHandler != null;
        // perhaps limit amount of devices?
        if (ports.containsKey(port))
            throw new PortAllocatedException(port);
        ports.put(port, packetHandler);
    }

    /**
     * Unregisters a device from a specific port.
     *
     * @param port The port number to unregister
     */
    @DoNotTouch
    public static void unregisterDevice(int port) {
        assert 0 <= port && port < MAX_PORT;
        ports.remove(port);
    }

    /**
     * Sends a packet to a specific port.
     * The packet is routed through the channel to the appropriate packet consumer.
     *
     * @param port   The destination port
     * @param packet The packet to send
     * @throws UnknownPortException If the port is not registered
     */
    @DoNotTouch
    public static void sendPacket(int port, Packet packet) throws UnknownPortException {
        assert 0 <= port && port < MAX_PORT;
        assert packet != null;
        if (!ports.containsKey(port))
            throw new UnknownPortException(port);
        PacketConsumer receiver = ports.get(port);

        new Thread(() -> channel.route(receiver, packet)).start();
    }

    /**
     * Gets an available port number.
     *
     * @return An available port number
     * @throws NoFreePortException If there are no free ports available
     */
    @DoNotTouch
    public static int getFreePort() throws NoFreePortException {
        if (ports.size() >= MAX_PORT)
            throw new NoFreePortException();
        if (ports.size() >= Short.MAX_VALUE) {
            // probing atp will get a tad annoying
            for (int i = 0; i < MAX_PORT; i++) {
                if (!ports.containsKey(i))
                    return i;
            }
        }
        int port;
        do {
            port = ThreadLocalRandom.current().nextInt(0, MAX_PORT);
        } while (ports.containsKey(port));
        return port;
    }

    /**
     * Sets the channel used for routing packets.
     *
     * @param ch The channel to use
     */
    @DoNotTouch
    public static void setChannel(Channel ch) {
        InternetPool.channel = ch;
    }

}
