package h05.equipment;

import org.jetbrains.annotations.NotNull;

public class Axe implements Tool{

    private double durability;

    public Axe() {
        this.durability = 100.0;
    }

    @Override
    public double getMiningPower() {
        return 5;
    }

    @Override
    public @NotNull String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public @NotNull EquipmentCondition getCondition() {
        double stop = getDurability();

        if (stop == 0) {
            return EquipmentCondition.BROKEN;
        } else if (stop <= 40) {
            return EquipmentCondition.DAMAGED;
        } else if (stop <= 80) {
            return EquipmentCondition.USED;
        } else {
            return EquipmentCondition.NEW;
        }
    }

    @Override
    public double getDurability() {
        return this.durability;
    }

    @Override
    public void setDurability(double durability) {
        if (durability > 100) {
            this.durability = 100;
        } else if (durability < 0) {
            this.durability = 0;
        } else {
            this.durability = durability;
        }
    }

    @Override
    public void reduceDurability(double amount) {
        setDurability(getDurability() - amount);
    }
}
