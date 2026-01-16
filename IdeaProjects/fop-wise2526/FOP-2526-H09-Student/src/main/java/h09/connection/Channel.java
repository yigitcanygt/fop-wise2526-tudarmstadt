package h09.connection;

import h09.packet.Packet;
import h09.packet.PacketType;
import h09.utils.PacketConsumer;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Represents a communication channel for routing packets to consumers.
 * Provides basic packet routing functionality that can be extended by subclasses.
 */
@DoNotTouch
public class Channel {

    /**
     * Internal method for routing {@link Packet Packets} to consumers.
     * Can be overridden by subclasses to implement custom routing behavior.
     *
     * @param consumer the packet consumer that will receive the packet
     * @param packet   the packet to be routed
     */
    @DoNotTouch
    protected void routeInner(PacketConsumer consumer, Packet packet) {
        consumer.accept(packet);
    }

    /**
     * Routes a {@link Packet} to a consumer.
     * {@link PacketType#CLOSE CLOSE} packets are always routed directly to
     * the consumer without any custom handling.
     *
     * @param consumer the packet consumer that will receive the packet
     * @param packet   the packet to be routed
     */
    @DoNotTouch
    public void route(PacketConsumer consumer, Packet packet) {
        if (packet.getHeader().type() == PacketType.CLOSE) {
            // no playing around with close
            consumer.accept(packet);
        } else {
            routeInner(consumer, packet);
        }
    }

}
