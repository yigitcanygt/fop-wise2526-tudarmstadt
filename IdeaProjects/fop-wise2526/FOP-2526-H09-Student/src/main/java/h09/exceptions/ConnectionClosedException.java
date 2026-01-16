package h09.exceptions;

import h09.connection.Connection;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Exception thrown when attempting to use a {@link Connection Connection}
 * that is already closed. This typically occurs when trying to send or
 * receive data on a closed connection.
 */
@DoNotTouch
public class ConnectionClosedException extends InternetException {

    /**
     * Constructs a new {@code ConnectionClosedException} with a standard message.
     */
    @DoNotTouch
    public ConnectionClosedException() {
        super("The connection is already closed!");
    }
}
