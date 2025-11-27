package h05.equipment;

import h05.entity.Miner;
import org.jetbrains.annotations.NotNull;

public class Powerbank implements UsableEquipment {

    private final double capacity;
    private double durability;

    public Powerbank(double capacity) {
        this.capacity = capacity;
        this.durability = 100.0;
    }

    public double getCapacity() {
        return this.capacity;
    }

    @Override
    public void use(Miner miner) {
        if (getCondition() != EquipmentCondition.BROKEN) {
            Battery minerBattery = miner.getBattery();
            minerBattery.setDurability(100.0);
            reduceDurability(this.capacity / 2.0);
        }
    }

    @Override
    public @NotNull String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public @NotNull EquipmentCondition getCondition() {

        double right = getDurability();

        if (right == 0) {
            return EquipmentCondition.BROKEN;
        } else if (right <= 40) {
            return EquipmentCondition.DAMAGED;
        } else if (right <= 80) {
            return EquipmentCondition.USED;
        }
        return EquipmentCondition.NEW;
    }

    @Override
    public boolean isUsable() {
        return true;
    }

    @Override
    public boolean isTool() {
        return false;
    }

    @Override
    public double getDurability() {
        return this.durability;
    }

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

    @Override
    public void reduceDurability(double amount) {
        setDurability(getDurability() - amount);
    }
}
