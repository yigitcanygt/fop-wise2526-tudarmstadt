package h05.entity;

import h05.equipment.Equipment;
import h05.equipment.UsableEquipment;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * An entity that can be equipped with various types of equipment.
 */
@DoNotTouch
public interface Equipable {

    /**
     * Returns all equipments of this entity.
     *
     * @return all equipments of this entity
     */
    @DoNotTouch
    Equipment[] getEquipments();

    /**
     * Returns the number of equipments this entity has.
     *
     * @return the number of equipments this entity has
     */
    @DoNotTouch
    int getNumberOfEquipments();

    /**
     * Uses the equipment at the specified index.
     *
     * @param index the index of the equipment to use
     * @see UsableEquipment
     */
    @DoNotTouch
    void use(int index);

    /**
     * Equips the specified equipment to this entity. If the equipment is already equipped, it will be replaced and
     * if the entity has no free slots, no action will be taken.
     *
     * @param equipment the equipment to equip
     */
    @DoNotTouch
    void equip(@NotNull Equipment equipment);

    /**
     * Unequips the equipment at the specified index.
     *
     * @param index the index of the equipment to unequip
     */
    @DoNotTouch
    void unequip(int index);
}
