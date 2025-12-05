package h06;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * a piece of wood
 */
@DoNotTouch
public class Wood {
    final double strength;
    double cuttingDepth;

    /**
     * Creates a new piece of wood
     *
     * @param strength     the strength of the piece of wood
     * @param cuttingDepth the initial width of the piece of wood
     */
    public Wood(double strength, double cuttingDepth) {
        this.strength = strength;
        this.cuttingDepth = cuttingDepth;
    }
}
