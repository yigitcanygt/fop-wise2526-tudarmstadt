package h13.ppm.image;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * An in-memory representation of a PPM image.
 *
 * @param header    the image's header containing meta-data such as dimensions
 * @param pixelData the image's pixel colors; row-based
 */
@DoNotTouch
public record PPMImage(PPMHeader header, PixelColor[][] pixelData) {

    public static final int MAX_COLOR = 255;

    public PPMImage {
        if (header == null) {
            throw new IllegalArgumentException("header must not be null");
        }

        if (pixelData == null) {
            throw new IllegalArgumentException("pixelData must not be null");
        }

        int width = header.width();
        int height = header.height();

        if (pixelData.length != height) {
            throw new IllegalArgumentException("Number of pixel rows must equal height");
        }

        for (PixelColor[] row : pixelData) {
            if (row == null) {
                throw new IllegalArgumentException("Pixel data must not contain null-rows");
            }

            if (row.length != width) {
                throw new IllegalArgumentException("Number of pixels in one row must equal width");
            }
        }
    }

}
