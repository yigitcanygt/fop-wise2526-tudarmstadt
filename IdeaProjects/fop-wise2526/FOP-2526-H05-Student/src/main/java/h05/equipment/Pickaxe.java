package h05.equipment;

import org.jetbrains.annotations.NotNull;

public class Pickaxe implements Tool {

    private double durability;

    public Pickaxe() {
        this.durability = 100.0;
    }

    @Override
    public double getMiningPower() {
        return 15;
    }

    @Override
    public @NotNull String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public @NotNull EquipmentCondition getCondition() {
        double currentDurability = getDurability();

        if (currentDurability == 0) {
            return EquipmentCondition.BROKEN;
        }
        if (currentDurability >= 1 && currentDurability <= 40) {
            return EquipmentCondition.DAMAGED;
        }
        if (currentDurability >= 41 && currentDurability <= 80) {
            return EquipmentCondition.USED;
        }
        return EquipmentCondition.NEW;
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
