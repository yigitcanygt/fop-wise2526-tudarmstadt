package h09.exceptions.port;

import h09.exceptions.InternetException;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Exception thrown when attempting to allocate a port that is already in use.
 * This typically occurs when trying to bind to a specific port that another connection is using.
 */
@DoNotTouch
public class PortAllocatedException extends InternetException {

    /**
     * Constructs a new {@code PortAllocatedException} with a message indicating the port number.
     *
     * @param port the port number that is already allocated
     */
    @DoNotTouch
    public PortAllocatedException(int port) {
        super("Port " + port + " is already allocated!");
    }
}
