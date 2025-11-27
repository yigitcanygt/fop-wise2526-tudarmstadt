package h05.equipment;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * A tool represents a piece of equipment that can be used for mining.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public interface Tool extends Equipment {

    /**
     * Returns the mining power of this tool.
     *
     * @return the mining power of this tool
     */
    @DoNotTouch
    double getMiningPower();

    @DoNotTouch
    @Override
    default boolean isUsable() {
        return false;
    }

    @DoNotTouch
    @Override
    default boolean isTool() {
        return true;
    }
}
