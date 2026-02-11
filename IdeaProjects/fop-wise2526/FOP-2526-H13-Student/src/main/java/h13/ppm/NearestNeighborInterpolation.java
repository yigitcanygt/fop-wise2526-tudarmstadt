package h13.ppm;

import h13.ppm.image.PPMHeader;
import h13.ppm.image.PPMImage;
import h13.ppm.image.PixelColor;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * Class to provide class method that implements nearest neighbor-interpolation of images.
 */
public final class NearestNeighborInterpolation {

    @DoNotTouch
    private NearestNeighborInterpolation() {
    }

    /**
     * Scales an image using nearest neighbor-interpolation given a scale factor.
     * The new image's dimensions are the originals scaled by scale factor (rounded towards 0).
     *
     * @param original    the original image to be scaled
     * @param scaleFactor the factor by which the image's dimensions should be scaled
     * @return a new image scaled by scaleFactor
     */
    @StudentImplementationRequired("H13.1.2")
    public static PPMImage scaleImage(PPMImage original, float scaleFactor) {
        PPMHeader originalHeader = original.header();
        int originalWidth = originalHeader.width();
        int originalLength = originalHeader.height();
        int newWidth = (int) (originalWidth * scaleFactor);
        int newLength = (int) (originalLength * scaleFactor);
        PixelColor[][] newPixelMatris = new PixelColor[newLength][newWidth];
        for (int newLine = 0; newLine < newLength; newLine++) {
            for (int newColumn = 0; newColumn < newWidth; newColumn++) {
                float originalColumFloat = newColumn / scaleFactor;
                float originalLineFloat = newLine / scaleFactor;
                int originalColumn = Math.round(originalColumFloat);
                int originalLine = Math.round(originalLineFloat);
                originalColumn = Math.clamp(originalColumn, 0, originalWidth - 1);
                originalLine = Math.clamp(originalLine, 0, originalLength - 1);
                newPixelMatris[newLine][newColumn] =
                    original.pixelData()[originalLine][originalColumn];
            }
        }
        PPMHeader newLine = new PPMHeader(newWidth, newLength);
        return new PPMImage(newLine, newPixelMatris);
    }
}
