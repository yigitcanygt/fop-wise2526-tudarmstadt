package h05.base.game;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * An interface representing entities that are updated based on ticks.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public interface TickBased {

    /**
     * Returns the number of ticks after which the entity should be updated.
     *
     * @return the update delay in ticks
     */
    @DoNotTouch
    default int getUpdateDelay() {
        return 0;
    }
}
