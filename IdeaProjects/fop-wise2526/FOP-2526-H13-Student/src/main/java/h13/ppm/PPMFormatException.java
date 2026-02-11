package h13.ppm;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * An exception that is thrown when an image can not be parsed due to not conforming to the PPM specification.
 */
@DoNotTouch
public class PPMFormatException extends RuntimeException {
    /**
     * Creates a new {@link PPMFormatException} with the specified message.
     *
     * @param message the exception's message
     */
    public PPMFormatException(String message) {
        super(message);
    }
}
