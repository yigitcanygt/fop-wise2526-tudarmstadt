package h05.entity;

import fopbot.Direction;
import h05.base.game.GameSettings;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.Point;

/**
 * A repair bot that can break walls to move freely in the world.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public class WallBreakerRepairBot extends AbstractRepairBot {

    /**
     * Constructs a new {@link WallBreakerRepairBot} instance with the specified position, game settings, and radius.
     *
     * @param x        the x-coordinate of the repair bot
     * @param y        the y-coordinate of the repair bot
     * @param settings the game settings of this repair bot, which provides access to the world and other entities
     * @param radius   the radius of this repair bot, which determines how far it can scan for entities to repair
     */
    @DoNotTouch
    public WallBreakerRepairBot(int x, int y, GameSettings settings, int radius) {
        super(x, y, settings, radius);
    }

    @DoNotTouch
    @Override
    protected void move(@NotNull Point point) {
        final int destX = point.x;
        final int destY = point.y;
        final GameSettings settings = getGameSettings();

        // Move to x coordinate
        if (destX != getX()) {
            Direction direction = destX < getX() ? Direction.LEFT : Direction.RIGHT;
            while (getDirection() != direction) {
                turnLeft();
            }
            while (destX != getX()) {
                if (!isFrontClear()) {
                    int wallX = getX();
                    if(getDirection() == Direction.LEFT) wallX--;
                    settings.removeEntity(settings.getWallAt(wallX, getY(), false));
                }
                move();
            }
        }
        // Move to y coordinate
        if (destY != getY()) {
            Direction direction = destY < getY() ? Direction.DOWN : Direction.UP;
            while (getDirection() != direction) {
                turnLeft();
            }
            while (destY != getY()) {
                if (!isFrontClear()) {
                    int wallY = getY();
                    if(getDirection() == Direction.DOWN) wallY--;
                    settings.removeEntity(settings.getWallAt(getX(), wallY, true));
                }
                move();
            }
        }
    }
}
