package h05.mineable;

import h05.Durable;
import h05.equipment.Tool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * A mineable entity represents a resources in a world which can be mined.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public interface Mineable extends Durable {

    /**
     * Returns the name of this mineable entity.
     *
     * @return the name of this mineable entity
     */
    @DoNotTouch
    @NotNull
    String getName();

    /**
     * Returns the progress of mining this entity.
     *
     * @return the progress of mining this entity
     */
    @NotNull MiningProgress getProgress();


    /**
     * Returns {@code true} if this mineable entity is completely mined, otherwise {@code false} when a mining operation
     * is ongoing.
     *
     * @param tool the tool used for mining, or {@code null} if no tool is used
     * @return {@code true} if this mineable entity is completely mined, otherwise {@code false}
     */
    @DoNotTouch
    boolean onMined(@Nullable Tool tool);
}
