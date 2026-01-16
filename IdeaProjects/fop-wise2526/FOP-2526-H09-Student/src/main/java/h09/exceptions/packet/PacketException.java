package h09.exceptions.packet;

import h09.packet.Packet;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Base exception class for all {@link Packet Packet}-related exceptions.
 * Provides a common superclass for exceptions that occur during packet handling.
 */
@DoNotTouch
public class PacketException extends Exception {

    /**
     * Constructs a new PacketException with a detailed message.
     *
     * @param message the detail message describing the exception
     */
    @DoNotTouch
    public PacketException(String message) {
        super("The following exception occurred when handling a packet: " + message);
    }
}
