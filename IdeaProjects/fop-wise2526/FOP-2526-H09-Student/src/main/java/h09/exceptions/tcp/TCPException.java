package h09.exceptions.tcp;

import h09.exceptions.InternetException;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Exception thrown when an error occurs during TCP connection operations.
 * This exception is specific to TCP protocol-related issues.
 */
@DoNotTouch
public class TCPException extends InternetException {

    /**
     * Constructs a new {@code TCPException} with a detailed message.
     *
     * @param message the detail message describing the TCP-related error
     */
    @DoNotTouch
    public TCPException(String message) {
        super("The following error occurred during TCP connection: " + message);
    }

}
