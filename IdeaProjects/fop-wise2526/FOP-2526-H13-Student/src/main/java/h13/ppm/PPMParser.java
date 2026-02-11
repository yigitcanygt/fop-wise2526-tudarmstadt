package h13.ppm;

import h13.ppm.image.PPMHeader;
import h13.ppm.image.PPMImage;
import h13.ppm.image.PixelColor;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.io.IOException;
import java.io.InputStream;

/**
 * Parses a PPM encoded image from a given {@link InputStream}.
 */
public class PPMParser implements AutoCloseable {

    private final InputStream input;

    /**
     * Creates a new {@link PPMParser} that will read an image from the given {@link InputStream}
     *
     * @param input the {@link InputStream} to read from
     */
    @DoNotTouch
    public PPMParser(InputStream input) {
        this.input = input;
    }

    /**
     * Parses one complete image from the input.
     *
     * @return the parsed image
     * @throws IOException if an I/O error occurs
     */
    @StudentImplementationRequired("H13.1.1")
    public PPMImage parse() throws IOException {
        PPMHeader titel = parseHeader();
        PixelColor[][] pixelData = parsePixelData(titel.width(), titel.height());
        return new PPMImage(titel, pixelData);
    }

    /**
     * Reads as many bytes as expected according to the image's width and height.
     *
     * @param width  the image's expected width
     * @param height the image's expected height
     * @return a 2-dimensional array containing pixel rows of the whole image
     * @throws IOException if an I/O error occurs
     */
    @StudentImplementationRequired("H13.1.1")
    public PixelColor[][] parsePixelData(int width, int height) throws IOException {
        PixelColor[][] pixelMatris = new PixelColor[height][width];
        for (int lineIndex = 0; lineIndex < height; lineIndex++) {
            for (int columnIndex = 0; columnIndex < width; columnIndex++) {
                int redValue = input.read();
                int greenValue = input.read();
                int blueValue = input.read();
                if (redValue == -1 || greenValue == -1 || blueValue == -1) {
                    throw new PPMFormatException("Unexpected result: Missing pixel data.");
                }
                pixelMatris[lineIndex][columnIndex] = new PixelColor(
                    (byte) redValue,
                    (byte) greenValue,
                    (byte) blueValue
                );
            }
        }
        int remainingData = input.read();
        if (remainingData != -1) {
            throw new PPMFormatException("After all pixels have been read, the stream still contains data.");
        }
        return pixelMatris;
    }
    /**
     * Parses the header from the input and returns its relevant information.
     * Only max color values of 255 are allowed.
     *
     * @return the parsed header
     * @throws IOException if an I/O error occur
     */
    @StudentImplementationRequired("H13.1.1")
    public PPMHeader parseHeader() throws IOException {
        int firstByte = input.read();
        int secondByte = input.read();
        int thirdByte = input.read();
        if (firstByte != 'P' || secondByte != '6' || !Character.isWhitespace(thirdByte)) {
            throw new PPMFormatException("Invalid PPM format: Magic bytes must start with 'P6' and whitespace.");
        }
        int width = parseWhitespaceTerminatedDecimal();
        int height = parseWhitespaceTerminatedDecimal();
        int maximumColorValue = parseWhitespaceTerminatedDecimal();
        if (maximumColorValue != 255) {
            throw new PPMFormatException("The maximum color value should be 255, it was found: " + maximumColorValue);
        }
        return new PPMHeader(width, height);
    }

    /**
     * Parses an integer encoded in ASCII decimal as per PPM spec.
     * The decimals end is detected if there is a whitespace character.
     * The terminating whitespace character is also consumed from the stream
     *
     * @return the integer value of the parsed decimal
     * @throws IOException if an I/O error occur
     */
    @DoNotTouch
    private int parseWhitespaceTerminatedDecimal() throws IOException {
        StringBuilder decimal = new StringBuilder();

        int b;
        while (true) {
            b = input.read();
            if (b >= '0' && b <= '9') {
                int digit = b - '0';
                decimal.append(digit);
            } else if (Character.isWhitespace(b)) {
                break;
            } else {
                throw new PPMFormatException("Expected digit or whitespace");
            }
        }

        if (decimal.isEmpty()) {
            throw new PPMFormatException("Expected at least one decimal digit");
        }

        return Integer.parseInt(decimal.toString());
    }

    @DoNotTouch
    @Override
    public void close() throws IOException {
        input.close(); // close underlying stream
    }
}
