package h09.connection;

import h09.packet.Packet;
import h09.utils.PacketConsumer;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.util.Random;

/**
 * A channel implementation that simulates packet loss.
 * Randomly drops packets based on a configurable loss probability.
 */
@DoNotTouch
public class LossyChannel extends Channel {

    /**
     * Random number generator used to determine if a packet should be dropped.
     */
    @DoNotTouch
    private final Random random;

    /**
     * The probability of a packet being lost (dropped).
     * Value between 0.0 (no loss) and 1.0 (all packets lost).
     */
    @DoNotTouch
    private final double lossProbability;

    /**
     * Constructs a new LossyChannel with the specified random generator and loss probability.
     *
     * @param random          the random number generator to use
     * @param lossProbability the probability of packet loss (between 0.0 and 1.0)
     */
    @DoNotTouch
    public LossyChannel(Random random, double lossProbability) {
        this.random = random;
        this.lossProbability = lossProbability;
    }

    /**
     * Constructs a new LossyChannel with the specified random generator and default loss probability of 0.1.
     *
     * @param random the random number generator to use
     */
    @DoNotTouch
    public LossyChannel(Random random) {
        this(random, 0.1);
    }

    /**
     * Constructs a new LossyChannel with a new random generator and default loss probability of 0.1.
     */
    @DoNotTouch
    public LossyChannel() {
        this(new Random());
    }

    /**
     * Routes a packet to a consumer with a chance of dropping the packet.
     * The packet is dropped with probability equal to lossProbability.
     *
     * @param consumer the packet consumer that will receive the packet if not dropped
     * @param packet   the packet to be routed
     */
    @Override
    @DoNotTouch
    protected void routeInner(PacketConsumer consumer, Packet packet) {
        if (random.nextDouble() > lossProbability) {
            super.routeInner(consumer, packet);
        }
    }
}
