package h09.exceptions.port;

import h09.exceptions.InternetException;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Exception thrown when a service on a specific port cannot be resolved.
 * This typically occurs when trying to connect to a port that doesn't have an active service.
 */
@DoNotTouch
public class UnknownPortException extends InternetException {

    /**
     * Constructs a new {@code UnknownPortException} with a message indicating the port number.
     *
     * @param port the port number that could not be resolved
     */
    @DoNotTouch
    public UnknownPortException(int port) {
        super("Service on port " + port + " could not be resolved!");
    }
}
