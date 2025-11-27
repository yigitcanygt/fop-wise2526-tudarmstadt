package h05.equipment;

import h05.Durable;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Equipment is an object that enhances the status or abilities of the entity it is attached to.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
public interface Equipment extends Durable {
    /**
     * Returns the name of this equipment.
     *
     * @return the name of this equipment
     */
    @DoNotTouch
    @NotNull
    String getName();

    /**
     * Returns the condition of this equipment.
     *
     * @return the condition of this equipment
     */
    @NotNull EquipmentCondition getCondition();

    /**
     * Returns {@code true} if this equipment is usable, otherwise {@code false}.
     *
     * @return {@code true} if this equipment is usable, otherwise {@code false}
     * @see UsableEquipment
     */
    boolean isUsable();

    /**
     * Returns {@code true} if this equipment is a tool, otherwise {@code false}.
     *
     * @return {@code true} if this equipment is a tool, otherwise {@code false}
     * @see Tool
     */
    boolean isTool();
}
