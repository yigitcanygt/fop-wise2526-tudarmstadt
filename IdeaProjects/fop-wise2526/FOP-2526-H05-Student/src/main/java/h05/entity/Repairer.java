package h05.entity;

import h05.base.game.GameSettings;
import h05.base.game.TickBased;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.Point;

/**
 * A repairer is a robot that can repair other robots or entities in the world.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public interface Repairer extends TickBased {

    /**
     * Returns the game settings of this repairer.
     *
     * @return the game settings of this repairer
     */
    @DoNotTouch
    @NotNull
    GameSettings getGameSettings();

    /**
     * Returns the radius of this repairer, which determines how far it can scan for entities to repair.
     *
     * @return the radius of this repairer
     */
    @DoNotTouch
    int getRadius();

    /**
     * Scans the area around this repairer to find an entity that can be repaired.
     *
     * @return the point of the entity to be repaired, or {@code null} if no entity can be repaired
     */
    @DoNotTouch
    @Nullable
    Point scan();

    /**
     * Repairs the entity at the specified point if it is within the repairer's radius and can be repaired.
     *
     * <p>A broken battery or camera will be replaced by a new one and all broken usable equipment will be removed.
     *
     * @param point the point of the entity to be repaired
     */
    @DoNotTouch
    void repair(@NotNull Point point);
}
