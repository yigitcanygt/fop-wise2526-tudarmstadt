package h09.connection;

import h09.exceptions.InternetException;
import h09.exceptions.TimeoutException;
import h09.packet.Packet;
import h09.packet.PacketType;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Represents a network connection between two ports.
 * This class handles the sending and receiving of packets between a source port and a destination port.
 */
@DoNotTouch
public class Connection {

    /**
     * The source port of this connection.
     */
    @DoNotTouch
    private final int sourcePort;

    /**
     * The destination port of this connection.
     */
    @DoNotTouch
    private final int destinationPort;

    /**
     * Queue for storing packets received by this connection.
     */
    @DoNotTouch
    private final BlockingQueue<Packet> packetQueue;

    /**
     * Constructs a Connection with specified source port, destination port, and packet queue.
     *
     * @param sourcePort      The source port for this connection
     * @param destinationPort The destination port for this connection
     * @param packetQueue     The queue to store received packets
     */
    @DoNotTouch
    public Connection(int sourcePort, int destinationPort, BlockingQueue<Packet> packetQueue) {
        this.sourcePort = sourcePort;
        this.destinationPort = destinationPort;
        this.packetQueue = packetQueue;
    }

    /**
     * Constructs a Connection with specified source port and destination port.
     * Creates a new LinkedBlockingQueue for packet storage.
     *
     * @param sourcePort      The source port for this connection
     * @param destinationPort The destination port for this connection
     */
    @DoNotTouch
    public Connection(int sourcePort, int destinationPort) {
        this(sourcePort, destinationPort, new LinkedBlockingQueue<>());
    }

    /**
     * Adds a packet to this connection's packet queue.
     *
     * @param packet The packet to add to the queue
     */
    @DoNotTouch
    public void addPacket(Packet packet) {
        packetQueue.add(packet);
    }

    /**
     * Waits indefinitely or until interruption for a packet to arrive in the queue.
     * This method blocks until a packet is available.
     *
     * @return The received packet
     */
    @DoNotTouch
    public Packet waitForPacket() {
        try {
            return packetQueue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e); // mitigate to handler
        }
    }

    /**
     * Waits for a packet to arrive in the queue with a specified timeout.
     *
     * @param timeout The maximum time to wait in milliseconds
     * @return The received packet
     * @throws TimeoutException If no packet is received within the timeout period
     */
    @DoNotTouch
    public Packet waitForPacketTimeout(long timeout) throws TimeoutException {
        try {
            Packet result = packetQueue.poll(timeout, TimeUnit.MILLISECONDS);
            if (result == null) {
                throw new TimeoutException("Timeout while waiting for packet!");
            }
            return result;
        } catch (InterruptedException e) {
            throw new RuntimeException(e); // mitigate to handler
        }
    }

    /**
     * Sends a packet to the destination port with the specified parameters.
     *
     * @param sequenceNumber The sequence number for the packet
     * @param type           The type of the packet
     * @param data           The data to include in the packet or null if it has no data
     * @throws InternetException If there is an error sending the packet
     */
    @DoNotTouch
    public void sendPacket(int sequenceNumber, PacketType type, String data) throws InternetException {
        InternetPool.sendPacket(destinationPort, new Packet(sourcePort, destinationPort, sequenceNumber, type, data));
    }

}
