package h05.base.entity;

import fopbot.FieldEntity;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Represents a fog entity in the game world that obscures visibility.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public class Fog extends FieldEntity {

    /**
     * Constructs a new {@link Fog} instance at the specified coordinates.
     *
     * @param x the x-coordinate of the fog entity
     * @param y the y-coordinate of the fog entity
     */
    @DoNotTouch
    public Fog(int x, int y) {
        super(x, y);
    }
}
