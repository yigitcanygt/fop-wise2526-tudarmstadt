package h05.base.entity;

import fopbot.FieldEntity;
import h05.equipment.Equipment;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Represents a gear entity in the game world that contains equipment.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public class Gear extends FieldEntity {

    /**
     * The equipment contained in this gear entity.
     */
    @DoNotTouch
    private final @NotNull Equipment equipment;

    /**
     * Constructs a new {@link Gear} instance at the specified coordinates with the given equipment.
     *
     * @param x         the x-coordinate of the gear entity
     * @param y         the y-coordinate of the gear entity
     * @param equipment the equipment contained in this gear entity
     */
    @DoNotTouch
    public Gear(int x, int y, @NotNull Equipment equipment) {
        super(x, y);
        this.equipment = equipment;
    }


    /**
     * Returns the equipment contained in this gear entity.
     *
     * @return the equipment
     */
    @DoNotTouch
    public @NotNull Equipment getEquipment() {
        return equipment;
    }
}
