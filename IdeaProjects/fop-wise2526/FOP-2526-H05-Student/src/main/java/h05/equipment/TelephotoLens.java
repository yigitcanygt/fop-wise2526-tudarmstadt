package h05.equipment;

import h05.entity.Miner;
import org.jetbrains.annotations.NotNull;

public class TelephotoLens implements UsableEquipment {

    private final int rangeEnhancement;
    private double durability;

    public TelephotoLens(int rangeEnhancement) {
        this.rangeEnhancement = rangeEnhancement;
        this.durability = 100.0;
    }

    public int getRangeEnhancement() {
        return this.rangeEnhancement;
    }

    @Override
    public void use(Miner miner) {
        if (getCondition() != EquipmentCondition.BROKEN) {
            Camera minerCamera = miner.getCamera();
            int currentRange = minerCamera.getVisibilityRange();
            minerCamera.setVisibilityRange(currentRange + this.rangeEnhancement);
            setDurability(0);
        }
    }

    @Override
    public @NotNull String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public @NotNull EquipmentCondition getCondition() {
        double instantRight = getDurability();

        if (instantRight == 0) {
            return EquipmentCondition.BROKEN;
        }
        if (instantRight >= 1 && instantRight <= 40) {
            return EquipmentCondition.DAMAGED;
        }
        if (instantRight >= 41 && instantRight <= 80) {
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
