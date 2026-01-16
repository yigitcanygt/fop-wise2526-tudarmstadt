package h09.exceptions;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Base exception class for all internet-related exceptions.
 * Provides a common superclass for exceptions that occur during network operations.
 */
@DoNotTouch
public class InternetException extends Exception {

    /**
     * Constructs a new {@code InternetException} with a detailed message.
     *
     * @param message the detail message describing the exception
     */
    @DoNotTouch
    public InternetException(String message) {
        super(message);
    }
}
