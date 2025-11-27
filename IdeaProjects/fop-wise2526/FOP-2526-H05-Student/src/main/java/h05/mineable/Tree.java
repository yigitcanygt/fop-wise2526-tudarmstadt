package h05.mineable;

import h05.equipment.Axe;
import h05.equipment.Pickaxe;
import h05.equipment.Tool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * Represents a tree that can be mined using tools.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
public class Tree implements Mineable {
private double durability;
    /**
     * Constructs a new {@link Tree} instance.
     */
    public Tree() {
        this.durability = 100.0;
    }

    @StudentImplementationRequired("H5.3")
    @Override
    public @NotNull MiningProgress getProgress() {
        double stability = getDurability();

        if (stability == 0) {
            return MiningProgress.COMPLETED;
        } else if (stability == 100) {
            return MiningProgress.UNSTARTED;
        } else {
            return MiningProgress.IN_PROGRESS;
        }
    }

    @StudentImplementationRequired("H5.3")
    @Override
    public @NotNull String getName() {
        return this.getClass().getSimpleName();
    }

    @StudentImplementationRequired("H5.3")
    @Override
    public boolean onMined(@Nullable Tool tool) {
        double reduction;

        if (tool instanceof Axe) {
            reduction = 4.0 * tool.getMiningPower();
        } else if (tool instanceof Pickaxe) {
            reduction = 3.0 * tool.getMiningPower();
        } else {
            reduction = 7.5;
        }

        reduceDurability(reduction);

        return getDurability() <= 0;
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
