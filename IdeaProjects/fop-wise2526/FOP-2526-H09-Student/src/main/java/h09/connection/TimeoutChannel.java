package h09.connection;

import h09.packet.Packet;
import h09.utils.PacketConsumer;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.util.Random;

/**
 * A specialized channel that introduces random delays when routing packets.
 * This simulates network latency or timeouts in a network connection.
 */
@DoNotTouch
public class TimeoutChannel extends Channel {

    /**
     * Random number generator used to determine delay times.
     */
    @DoNotTouch
    private final Random random;

    /**
     * Constructs a TimeoutChannel with a specified random number generator.
     *
     * @param random The random number generator to use for determining delays
     */
    @DoNotTouch
    public TimeoutChannel(Random random) {
        this.random = random;
    }

    /**
     * Constructs a TimeoutChannel with a new default random number generator.
     */
    @DoNotTouch
    public TimeoutChannel() {
        this(new Random());
    }

    /**
     * Routes a packet to a consumer with a random delay.
     * The delay can be up to 7000 milliseconds (7 seconds).
     *
     * @param consumer The packet consumer that will receive the packet
     * @param packet   The packet to route
     * @throws RuntimeException If the thread is interrupted during the delay
     */
    @Override
    @DoNotTouch
    protected void routeInner(PacketConsumer consumer, Packet packet) {
        try {
            Thread.sleep(random.nextLong(7000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        super.routeInner(consumer, packet);
    }
}
