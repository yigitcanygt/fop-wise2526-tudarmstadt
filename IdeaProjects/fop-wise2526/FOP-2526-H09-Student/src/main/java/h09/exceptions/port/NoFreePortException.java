package h09.exceptions.port;

import h09.exceptions.InternetException;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Exception thrown when no free port can be found for a new connection.
 * This typically occurs when all available ports are already in use.
 */
@DoNotTouch
public class NoFreePortException extends InternetException {

    /**
     * Constructs a new {@code NoFreePortException} with a standard message.
     */
    @DoNotTouch
    public NoFreePortException() {
        super("No free port could be found!");
    }
}
