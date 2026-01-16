package h09.utils;

import h09.exceptions.InternetException;
import h09.packet.Packet;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Functional interface for components that wait for and retrieve packets.
 * This interface allows for operations that may throw network-related exceptions.
 */
@FunctionalInterface
@DoNotTouch
public interface ThrowingPacketWaiter {

    /**
     * Attempts to retrieve a packet.
     *
     * @return the retrieved packet
     * @throws InternetException if an error occurs during packet retrieval
     */
    @DoNotTouch
    Packet tryGet() throws InternetException;

}
