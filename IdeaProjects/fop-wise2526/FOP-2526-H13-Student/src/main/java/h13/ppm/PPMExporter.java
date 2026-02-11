package h13.ppm;

import h13.ppm.image.PPMHeader;
import h13.ppm.image.PPMImage;
import h13.ppm.image.PixelColor;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A PPMExporter exports an in-memory {@link PPMImage} into a given {@link OutputStream}.
 * There is only one output.
 */
public class PPMExporter implements AutoCloseable {

    private final OutputStream output;

    /**
     * Creates a new {@link PPMExporter} with a given {@link OutputStream}.
     *
     * @param output the {@link OutputStream} to write the image's bytes to
     */
    @DoNotTouch
    public PPMExporter(OutputStream output) {
        this.output = output;
    }

    /**
     * Writes the image's bytes into the output
     *
     * @param image the image to be written
     * @throws IOException if an I/O error occurs
     */
    @StudentImplementationRequired("H13.1.3")
    public void export(PPMImage image) throws IOException{
        emitHeader(image.header());
        emitPixelData(image.pixelData());
        output.flush();
    }

    /**
     * Writes entire the header into output including magic bytes, width, height and max. color.
     * The terminating whitespace character is also written here.
     *
     * @param header the header to be written
     * @throws IOException if an I/O error occurs
     */
    @StudentImplementationRequired("H13.1.3")
    public void emitHeader(PPMHeader header) throws IOException {
        output.write((byte) 'P');
        output.write((byte) '6');
        output.write((byte) ' ');
        emitDecimal(header.width());
        output.write((byte) ' ');
        emitDecimal(header.height());
        output.write((byte) ' ');
        emitDecimal(PPMImage.MAX_COLOR);
        output.write((byte) ' ');
    }

    /**
     * Writes the bytes representing all pixels of an image.
     *
     * @param pixelData the image's pixel row-based
     * @throws IOException if an I/O error occurs
     */
    @StudentImplementationRequired("H13.1.3")
    public void emitPixelData(PixelColor[][] pixelData) throws IOException {
        for (int lineIndex = 0; lineIndex < pixelData.length; lineIndex++) {
            PixelColor[] linePixels = pixelData[lineIndex];
            for (int columnIndex = 0; columnIndex < linePixels.length; columnIndex++) {
                PixelColor pixel = linePixels[columnIndex];
                output.write(pixel.red());
                output.write(pixel.green());
                output.write(pixel.blue());
            }
        }
    }

    /**
     * Writes an integer in decimal format as per PPM spec.
     *
     * @param i the integer to be written as decimal
     * @throws IOException if an I/O error occurs
     */
    @DoNotTouch
    private void emitDecimal(int i) throws IOException {
        String s = String.valueOf(i);
        for (char c : s.toCharArray()) {
            output.write((byte) c);
        }
    }

    @DoNotTouch
    @Override
    public void close() throws IOException {
        output.close();
    }
}
