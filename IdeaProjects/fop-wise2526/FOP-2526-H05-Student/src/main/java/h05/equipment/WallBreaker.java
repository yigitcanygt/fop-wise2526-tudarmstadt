package h05.equipment;

import fopbot.Direction;
import fopbot.Wall;
import h05.entity.Miner;
import h05.base.game.GameSettings;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;


/**
 * Usable equipment that allows the miner entity to break walls in the world.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
public class WallBreaker implements UsableEquipment {

    private double durability;

    /**
     * Constructs a new {@link WallBreaker} instance.
     */
    public WallBreaker() {
        this.durability = 100.0;
    }

    @StudentImplementationRequired("H5.2.5")
    @Override
    public void use(@NotNull Miner miner) {
        if (getCondition() == EquipmentCondition.BROKEN) {
            return;
        }

        int currentX = miner.getX();
        int currentY = miner.getY();
        Direction dir = miner.getDirection();

        int targetX = currentX;
        int targetY = currentY;

        if (dir == Direction.UP) {
            targetY = currentY + 1;
        } else if (dir == Direction.DOWN) {
            targetY = currentY - 1;
        } else if (dir == Direction.LEFT) {
            targetX = currentX - 1;
        } else if (dir == Direction.RIGHT) {
            targetX = currentX + 1;
        }

        GameSettings settings = miner.getGameSettings();
        Wall[] wallsAtTarget = settings.getWallsAt(targetX, targetY);

        if (wallsAtTarget != null && wallsAtTarget.length > 0) {
            for (Wall wall : wallsAtTarget) {
                if (wall != null) {
                    settings.removeEntity(wall);
                }
            }
        }
    }

    @StudentImplementationRequired("H5.2")
    @Override
    public @NotNull String getName() {
        return getClass().getSimpleName();
    }

    @StudentImplementationRequired("H5.2.1")
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
