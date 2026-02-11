package h13.ppm.image;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * A PPM image's header containing important meta-data of the image.
 *
 * @param width  the image's width in pixels
 * @param height the image's height in pixels
 */
@DoNotTouch
public record PPMHeader(int width, int height) {
    public PPMHeader {
        if (width < 0) {
            throw new IllegalArgumentException("width must not be negative");
        }

        if (height < 0) {
            throw new IllegalArgumentException("height must not be negative");
        }
    }

}
