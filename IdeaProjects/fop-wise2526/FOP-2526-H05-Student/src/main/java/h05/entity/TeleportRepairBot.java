package h05.entity;

import h05.base.game.GameSettings;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.Point;

/**
 * A repair bot that can teleport to a specific point in the world.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public class TeleportRepairBot extends AbstractRepairBot {

    /**
     * Constructs a new {@link TeleportRepairBot} instance with the specified position, game settings, and radius.
     *
     * @param x        the x-coordinate of the repair bot
     * @param y        the y-coordinate of the repair bot
     * @param settings the game settings of this repair bot, which provides access to the world and other entities
     * @param radius   the radius of this repair bot, which determines how far it can scan for entities to repair
     */
    @DoNotTouch
    public TeleportRepairBot(int x, int y, GameSettings settings, int radius) {
        super(x, y, settings, radius);
    }

    @DoNotTouch
    @Override
    protected void move(@NotNull Point point) {
        setField(point.x, point.y);
    }
}
