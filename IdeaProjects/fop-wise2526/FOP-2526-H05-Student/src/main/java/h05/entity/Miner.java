package h05.entity;

import fopbot.Direction;
import h05.base.game.GameSettings;
import h05.base.game.TickBased;
import h05.base.mineable.Inventory;
import h05.equipment.Battery;
import h05.equipment.Camera;
import h05.equipment.EquipmentCondition;
import h05.equipment.Tool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.Point;

/**
 * A miner is an extension of the FOPBot robot which can equip itself to improve its status and abilities.
 * Also, it can mine resources.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public interface Miner extends TickBased, Equipable {

    /**
     * Returns the game settings of this miner.
     *
     * @return the game settings of this miner
     */
    @DoNotTouch
    @NotNull GameSettings getGameSettings();

    /**
     * Returns all possible coordinates that this miner can see.
     *
     * @param x the x-coordinate of the miner to get the vision for
     * @param y the y-coordinate of the miner to get the vision for
     * @return an array of points representing the vision of the miner
     */
    @DoNotTouch
    @NotNull
    Point[] getVision(int x, int y);

    /**
     * Updates the vision of the miner based on its new position.
     *
     * @param oldX the old x-coordinate of the miner
     * @param oldY the old y-coordinate of the miner
     * @param newX the new x-coordinate of the miner
     * @param newY the new y-coordinate of the miner
     */
    @DoNotTouch
    void updateVision(int oldX, int oldY, int newX, int newY);

    /**
     * Returns the battery of this miner.
     *
     * @return the battery of this miner
     */
    @DoNotTouch
    @NotNull
    Battery getBattery();

    /**
     * Returns the camera of this miner.
     *
     * @return the camera of this miner
     */
    @DoNotTouch
    @NotNull
    Camera getCamera();

    /**
     * Returns the tool of this miner.
     *
     * @return the tool of this miner, or {@code null} if no tool is equipped
     */
    @DoNotTouch
    @Nullable
    Tool getTool();

    /**
     * Returns the inventory of this miner.
     *
     * @return the inventory of this miner
     */
    Inventory getInventory();

    /**
     * Returns the x-coordinate of this miner.
     *
     * @return the x-coordinate of this miner
     */
    @DoNotTouch
    int getX();

    /**
     * Returns the y-coordinate of this miner.
     *
     * @return the y-coordinate of this miner
     */
    @DoNotTouch
    int getY();

    /**
     * Returns the direction this miner is facing.
     *
     * @return the direction this miner is facing
     */
    @DoNotTouch
    @NotNull
    Direction getDirection();

    /**
     * Returns {@code true} if the battery is broken, {@code false} otherwise.
     *
     * @return {@code true} if the battery is broken, {@code false} otherwise
     */
    @DoNotTouch
    default boolean isBatteryBroken() {
        return getBattery().getCondition() == EquipmentCondition.BROKEN;
    }

    /**
     * Returns {@code true} if the camera is broken, {@code false} otherwise.
     *
     * @return {@code true} if the camera is broken, {@code false} otherwise
     */
    @DoNotTouch
    default boolean isCameraBroken() {
        return getCamera().getCondition() == EquipmentCondition.BROKEN;
    }

    /**
     * Performs a mining action in the direction this miner is facing if there is a mineable entity in front of it.
     */
    @DoNotTouch
    void mine();

    /**
     * Pick up the gear on the current field if there is any.
     */
    @DoNotTouch
    void pickGear();

    /**
     * Handles key input for the miner, allowing it to perform actions based on the provided parameters.
     *
     * @param direction     the direction in which the miner should move or perform an action, or {@code null} if no
     *                      movement is needed
     * @param selection     the selection index for the usable equipment to be used, or -1 if no selection is made
     * @param isPickingGear whether the miner is currently picking up gear
     * @param isMining      whether the miner is currently mining
     * @param isInfo        whether the miner is requesting information about its inventory
     */
    @DoNotTouch
    void handleKeyInput(
        @Nullable Direction direction,
        int selection,
        boolean isPickingGear,
        boolean isMining,
        boolean isInfo
    );
}
