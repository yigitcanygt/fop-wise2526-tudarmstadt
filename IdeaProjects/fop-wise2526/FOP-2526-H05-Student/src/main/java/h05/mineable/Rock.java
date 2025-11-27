package h05.mineable;

import h05.equipment.Axe;
import h05.equipment.Pickaxe;
import h05.equipment.Tool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * Represents a rock that can be mined using tools.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
public class Rock implements Mineable {

    private double durability;
    /**
     * Constructs a new {@link Rock} instance.
     */
    public Rock() {
        this.durability = 100.0;
    }

    @StudentImplementationRequired("H5.3")
    @Override
    public @NotNull String getName() {
        return getClass().getSimpleName();
    }

    @StudentImplementationRequired("H5.3")
    @Override
    public @NotNull MiningProgress getProgress() {
        double right = getDurability();

        if (right == 100) {
            return MiningProgress.UNSTARTED;
        } else if (right == 0) {
            return MiningProgress.COMPLETED;
        } else {
            return MiningProgress.IN_PROGRESS;
        }
    }

    @StudentImplementationRequired("H5.3")
    @Override
    public boolean onMined(@Nullable Tool tool) {
        double reductionAmount;

        if (tool == null) {
            reductionAmount = 5.0;
        } else if (tool instanceof Pickaxe) {
            reductionAmount = 2.0 * tool.getMiningPower();
        } else if (tool instanceof Axe) {
            reductionAmount = 1.5 * tool.getMiningPower();
        } else {
            reductionAmount = 5.0;
        }

        reduceDurability(reductionAmount);

        return getDurability() == 0;
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
