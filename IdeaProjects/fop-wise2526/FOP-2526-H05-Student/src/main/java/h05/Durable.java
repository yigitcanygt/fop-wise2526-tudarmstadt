package h05;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Represents an object that has durability, which can be reduced.
 *
 * <p>he durability is a value between 0 and 100 (inclusive) that represents the percentage of durability remaining,
 * where 100 means full durability and 0 means the object is broken.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public interface Durable {

    /**
     * Returns the current durability of this object.
     *
     * @return the current durability of this object
     */
    @DoNotTouch
    double getDurability();

    /**
     * Sets the durability of this object to the specified value.
     *
     * <p>The durability value must be between 0 and 100 (inclusive). If the value is outside this range,
     * it will be clamped to the nearest valid value (0 or 100).
     *
     * @param durability the new durability value for this object
     */
    @DoNotTouch
    void setDurability(double durability);

    /**
     * Reduces the durability of this object by the specified amount.
     *
     * <p>If the resulting durability is less than 0, it will be set to 0.
     *
     * @param amount the amount to reduce the durability by
     */
    @DoNotTouch
    void reduceDurability(double amount);
}
