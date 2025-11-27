package h05.base.entity;

import fopbot.Block;
import fopbot.World;
import h05.equipment.Tool;
import h05.mineable.Mineable;
import h05.mineable.MiningProgress;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Represents a loot block in the game world that can be mined by a miner.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public class Loot extends Block {

    /**
     * THe underlying mineable object that represents the loot block.
     */
    @DoNotTouch
    private final @NotNull Mineable mineable;

    /**
     * A decorator for the mineable object that allows us do UI related operations in the background.
     */
    @DoNotTouch
    private final @NotNull Mineable decorator;

    /**
     * Constructs a new {@link Loot} instance at the specified coordinates with the given mineable object.
     *
     * @param x        the x-coordinate of the loot block
     * @param y        the y-coordinate of the loot block
     * @param mineable the mineable object that represents the loot block
     */
    @DoNotTouch
    public Loot(int x, int y, @NotNull Mineable mineable) {
        super(x, y);
        this.mineable = mineable;
        this.decorator = new Mineable() {

            @Override
            public @NotNull String getName() {
                return mineable.getName();
            }

            @Override
            public @NotNull MiningProgress getProgress() {
                return mineable.getProgress();
            }

            @SuppressWarnings("UnstableApiUsage")
            @Override
            public boolean onMined(@Nullable Tool tool) {
                boolean isMined = mineable.onMined(tool);
                if (isMined) {
                    World.getGlobalWorld().getGuiPanel().updateGui();
                }
                return isMined;
            }

            @Override
            @DoNotTouch
            public double getDurability() {
                return mineable.getDurability();
            }

            @Override
            @DoNotTouch
            public void setDurability(double durability) {
                mineable.setDurability(durability);
            }

            @Override
            @DoNotTouch
            public void reduceDurability(double amount) {
                mineable.reduceDurability(amount);
            }

            @Override
            @DoNotTouch
            public int hashCode() {
                return mineable.hashCode();
            }

            @Override
            @DoNotTouch
            public boolean equals(Object other) {
                return this == other || (other instanceof Mineable && mineable.equals(other));
            }

            @Override
            @DoNotTouch
            public String toString() {
                return mineable.toString();
            }
        };
    }

    /**
     * Returns the mineable object that represents the loot block.
     *
     * @return the mineable object of this loot block
     */
    @DoNotTouch
    public @NotNull Mineable getMineable() {
        return mineable;
    }

    /**
     * Returns the decorator for the mineable object that allows UI related operations.
     *
     * @return the decorator for the mineable object
     */
    @DoNotTouch
    public @NotNull Mineable getDecorator() {
        return decorator;
    }
}
