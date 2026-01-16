package h09.utils;

import h09.packet.Packet;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Functional interface for components that can consume packets.
 * Used for handling packet processing in the network communication system.
 */
@FunctionalInterface
@DoNotTouch
public interface PacketConsumer {

    /**
     * Accepts and processes a packet.
     *
     * @param packet the packet to be processed
     */
    @DoNotTouch
    void accept(Packet packet);

}
