package h09.exceptions;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Exception thrown when a network operation times out.
 * This typically occurs when waiting for a response that doesn't arrive within the expected time frame.
 */
@DoNotTouch
public class TimeoutException extends InternetException {

    /**
     * Constructs a new {@code TimeoutException} with a detailed message.
     *
     * @param message the detail message describing the timeout
     */
    @DoNotTouch
    public TimeoutException(String message) {
        super(message);
    }

}
