package h05.equipment;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * A battery is a type of equipment that defines the lifetime of an entity.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
public class Battery implements Equipment {

    private double durability;
    /**
     * Constructs a new {@link Battery} instance.
     */
    public Battery() {
        this.durability = 100.0;
    }

    /**
     * Increases the durability of this battery by the specified value.
     *
     * @param value the value to increase the durability by
     */
    @StudentImplementationRequired("H5.2.2")
    public void increaseDurability(double value) {
        if (getCondition() != EquipmentCondition.BROKEN) {
            setDurability(getDurability() + value);
        }
    }

    @StudentImplementationRequired("H5.2")
    @Override
    public @NotNull String getName() {
        return this.getClass().getSimpleName();
    }

    @StudentImplementationRequired("H5.2.1")
    @Override
    public @NotNull EquipmentCondition getCondition() {
        double condition = getDurability();
        if (condition == 0) {
            return EquipmentCondition.BROKEN;
        }
        if (condition >= 1 && condition <= 40) {
            return EquipmentCondition.DAMAGED;
        }
        if (condition >= 41 && condition <= 80) {
            return EquipmentCondition.USED;
        }
        return EquipmentCondition.NEW;
    }

    @Override
    public boolean isUsable() {
        return false;
    }

    @Override
    public boolean isTool() {
        return false;
    }

    @StudentImplementationRequired("H5.1")
    @Override
    public double getDurability() {
        return this.durability;
    }

    @StudentImplementationRequired("H5.1")
    @Override
    public void setDurability(double durability) {
        if (durability < 0) {
            this.durability = 0;
        } else if (durability > 100) {
            this.durability = 100;
        } else {
            this.durability = durability;
        }
    }

    @StudentImplementationRequired("H5.1")
    @Override
    public void reduceDurability(double amount) {
        setDurability(getDurability() - amount);
    }
}
