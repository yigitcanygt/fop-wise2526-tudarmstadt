package h05.base.game;

import fopbot.FieldEntity;
import fopbot.Wall;
import h05.entity.Miner;
import h05.equipment.Battery;
import h05.equipment.Camera;
import h05.equipment.Equipment;
import h05.equipment.Tool;
import h05.equipment.UsableEquipment;
import h05.mineable.Mineable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Defines the settings, management and interactions for the game environment.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public interface GameSettings {

    /**
     * Places a fog entity at the specified coordinates.
     *
     * @param x the x-coordinate where the fog should be placed
     * @param y the y-coordinate where the fog should be placed
     */
    @DoNotTouch
    void placeFog(int x, int y);

    /**
     * Removes the fog entity at the specified coordinates.
     *
     * @param x the x-coordinate of the fog to be removed
     * @param y the y-coordinate of the fog to be removed
     */
    @DoNotTouch
    void removeFog(int x, int y);

    /**
     * Converts the given equipment to a Battery if applicable.
     *
     * @param equipment the equipment to convert
     * @return the Battery if the equipment is a Battery, {@code null} otherwise
     */
    @DoNotTouch
    @Nullable Battery toBattery(Equipment equipment);

    /**
     * Converts the given equipment to a Camera if applicable.
     *
     * @param equipment the equipment to convert
     * @return the Camera if the equipment is a Camera, {@code null} otherwise
     */
    @DoNotTouch
    @Nullable Camera toCamera(Equipment equipment);

    /**
     * Converts the given equipment to a Tool if applicable.
     *
     * @param equipment the equipment to convert
     * @return the Tool if the equipment is a Tool, {@code null} otherwise
     */
    @DoNotTouch
    @Nullable Tool toTool(Equipment equipment);

    /**
     * Converts the given equipment to a UsableEquipment if applicable.
     *
     * @param equipment the equipment to convert
     * @return the UsableEquipment if the equipment is usable, {@code null} otherwise
     */
    @DoNotTouch
    @Nullable UsableEquipment toUsableEquipment(Equipment equipment);

    /**
     * Retrieves the mineable entity at the specified coordinates.
     *
     * @param x the x-coordinate of the mineable entity
     * @param y the y-coordinate of the mineable entity
     * @return the mineable entity at the specified coordinates, or {@code null} if none exists
     */
    @DoNotTouch
    @Nullable Mineable getMineableAt(int x, int y);

    /**
     * Retrieves the usable equipment at the specified coordinates.
     *
     * @param x the x-coordinate of the usable equipment
     * @param y the y-coordinate of the usable equipment
     * @return the usable equipment at the specified coordinates, or {@code null} if none exists
     */
    @DoNotTouch
    @Nullable UsableEquipment getUsableEquipmentAt(int x, int y);

    /**
     * Retrieves and removes the gear at the specified coordinates from the game world.
     *
     * @param x the x-coordinate of the gear
     * @param y the y-coordinate of the gear
     * @return the equipment contained in the gear if it exists, or {@code null} if no gear is present at the
     * coordinates
     */
    @DoNotTouch
    @Nullable Equipment getAndRemoveGearAt(int x, int y);

    /**
     * Places a gear entity containing the specified equipment at the given coordinates in the game world.
     *
     * @param x         the x-coordinate where the gear should be placed
     * @param y         the y-coordinate where the gear should be placed
     * @param equipment the equipment to be placed in the gear entity
     */
    @DoNotTouch
    void placeGearAt(int x, int y, Equipment equipment);

    /**
     * Retrieves the loot (mineable entity) at the specified coordinates.
     *
     * @param x the x-coordinate of the loot
     * @param y the y-coordinate of the loot
     * @return the mineable entity representing the loot at the specified coordinates, or {@code null} if no loot exists
     */
    @DoNotTouch
    @Nullable Mineable getLootAt(int x, int y);

    /**
     * Retrieves the miner at the specified coordinates.
     *
     * @param x the x-coordinate of the miner
     * @param y the y-coordinate of the miner
     * @return the miner at the specified coordinates, or {@code null} if no miner exists
     */
    @DoNotTouch
    @Nullable Miner getMinerAt(int x, int y);

    /**
     * Retrieves the wall at the specified coordinates and orientation.
     *
     * @param x          the x-coordinate of the wall
     * @param y          the y-coordinate of the wall
     * @param horizontal {@code true} if the wall is horizontal, {@code false} if it is vertical
     * @return the wall at the specified coordinates and orientation, or {@code null} if no wall exists
     */
    @DoNotTouch
    default @Nullable Wall getWallAt(int x, int y, boolean horizontal) {
        final Wall[] walls = getWallsAt(x, y);
        for (Wall wall : walls) {
            if (wall.isHorizontal() == horizontal) {
                return wall;
            }
        }
        return null;
    }

    /**
     * Retrieves all walls at the specified coordinates.
     *
     * @param x the x-coordinate of the walls
     * @param y the y-coordinate of the walls
     * @return an array of walls at the specified coordinates, which may be empty if no walls exist
     */
    @DoNotTouch
    @NotNull Wall[] getWallsAt(int x, int y);

    /**
     * Removes the specified entity from the game world.
     *
     * @param entity the entity to be removed
     */
    @DoNotTouch
    void removeEntity(@NotNull FieldEntity entity);

    /**
     * Updates the UI.
     */
    @DoNotTouch
    void update();
}
