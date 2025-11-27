package h05.equipment;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Represents the condition of equipment.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public enum EquipmentCondition {

    /**
     * Indicates that the equipment is new and has not been used yet.
     */
    @DoNotTouch
    NEW,

    /**
     * Indicates that the equipment has been used but is still in good condition.
     */
    @DoNotTouch
    USED,

    /**
     * Indicates that the equipment is damaged but still usable.
     */
    @DoNotTouch
    DAMAGED,

    /**
     * Indicates that the equipment is broken and cannot be used.
     */
    @DoNotTouch
    BROKEN;

    /**
     * Constructs a new {@link EquipmentCondition} instance.
     */
    @DoNotTouch
    EquipmentCondition() {
    }
}
