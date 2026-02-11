package h13.ppm.image;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * An RGB-color an image's pixel can have.
 *
 * @param red   the red component
 * @param green the green component
 * @param blue  the blue component
 */
@DoNotTouch
public record PixelColor(byte red, byte green, byte blue) {

}
