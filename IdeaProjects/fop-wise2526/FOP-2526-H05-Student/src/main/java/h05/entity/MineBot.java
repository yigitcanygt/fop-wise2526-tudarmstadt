package h05.entity;

import fopbot.Direction;
import fopbot.Robot;
import h05.base.game.GameSettings;
import h05.base.mineable.BasicInventory;
import h05.base.mineable.Inventory;
import h05.base.ui.InfoPopup;
import h05.equipment.Battery;
import h05.equipment.Camera;
import h05.equipment.Equipment;
import h05.equipment.Tool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.awt.Point;

/**
 * A basic implementation of a mining robot that can mine resources in the world.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public class MineBot extends Robot implements Miner {

    /**
     * The default capacity of the mine bot, which determines how much equipment it can hold.
     */
    @DoNotTouch
    public static final int DEFAULT_CAPACITY = 4;

    /**
     * The game settings of this mine bot, which provides access to the world and other entities.
     */
    @DoNotTouch
    private final @NotNull GameSettings settings;

    /**
     * The array of equipments that this mine bot can hold, which can include empty slots if it has not been fully
     * equipped.
     */
    @DoNotTouch
    private final @NotNull Equipment[] equipments;

    /**
     * The inventory of this mine bot, which holds the resources it has mined.
     */
    @DoNotTouch
    private final @NotNull Inventory inventory;

    /**
     * The index of the next available slot in the equipments array, which is used to add new equipment.
     */
    @DoNotTouch
    private int nextIndex;

    /**
     * The battery of this mine bot, which determines the lifetime and energy of the bot.
     */
    @DoNotTouch
    private @NotNull Battery battery;

    /**
     * The camera of this mine bot, which determines the visibility range of the bot.
     */
    @DoNotTouch
    private @NotNull Camera camera;

    /**
     * The tool of this mine bot, which can be used to mine resources.
     */
    @DoNotTouch
    private @Nullable Tool tool;

    /**
     * Constructs a new {@link MineBot} instance with the specified position, game settings, and capacity.
     *
     * @param x        the x-coordinate of the mine bot
     * @param y        the y-coordinate of the mine bot
     * @param settings the game settings of this mine bot, which provides access to the world and other entities
     * @param capacity the capacity of this mine bot, which determines how much equipment it can hold
     */
    @DoNotTouch
    public MineBot(int x, int y, @NotNull GameSettings settings, int capacity) {
        super(x, y);
        this.settings = settings;
        this.equipments = new Equipment[capacity];
        this.battery = new Battery();
        this.camera = new Camera();
        this.equipments[0] = this.battery;
        this.equipments[1] = this.camera;
        nextIndex = 2;
        this.inventory = new BasicInventory();
        for (Point point : getVision(x, y)) {
            settings.removeFog(point.x, point.y);
        }
    }

    /**
     * Constructs a new {@link MineBot} instance with the specified position and game settings, using the default
     * capacity of {@value  DEFAULT_CAPACITY}.
     *
     * @param x        the x-coordinate of the mine bot
     * @param y        the y-coordinate of the mine bot
     * @param settings the game settings of this mine bot, which provides access to the world and other entities
     */
    @DoNotTouch
    public MineBot(int x, int y, @NotNull GameSettings settings) {
        this(x, y, settings, DEFAULT_CAPACITY);
    }

    @StudentImplementationRequired("H5.4.2")
    @Override
    public @NotNull Point[] getVision(int x, int y) {
        int range = camera.getVisibilityRange();
        java.util.ArrayList<Point> visiblePoints = new java.util.ArrayList<>();

        int worldWidth = fopbot.World.getGlobalWorld().getWidth();
        int worldLength = fopbot.World.getGlobalWorld().getHeight();

        for (int dx = -range; dx <= range; dx++) {
            for (int dy = -range; dy <= range; dy++) {
                int checkX = x + dx;
                int checkY = y + dy;

                if (checkX >= 0 && checkX < worldWidth && checkY >= 0 && checkY < worldLength) {
                    double distance = Math.sqrt(dx * dx + dy * dy);

                    if (distance <= range) {
                        visiblePoints.add(new Point(checkX, checkY));
                    }
                }
            }
        }

        return visiblePoints.toArray(new Point[0]);
    }

    @StudentImplementationRequired("H5.4.2")
    @Override
    public void updateVision(int oldX, int oldY, int newX, int newY) {
        Point[] oldVision = getVision(oldX, oldY);
        Point[] newVision = getVision(newX, newY);

        for (Point oldPoint : oldVision) {
            boolean stillVisible = false;
            for (Point newPoint : newVision) {
                if (oldPoint.x == newPoint.x && oldPoint.y == newPoint.y) {
                    stillVisible = true;
                    break;
                }
            }
            if (!stillVisible) {
                settings.placeFog(oldPoint.x, oldPoint.y);
            }
        }

        for (Point newPoint : newVision) {
            boolean wasVisible = false;
            for (Point oldPoint : oldVision) {
                if (newPoint.x == oldPoint.x && newPoint.y == oldPoint.y) {
                    wasVisible = true;
                    break;
                }
            }
            if (!wasVisible) {
                settings.removeFog(newPoint.x, newPoint.y);
            }
        }
    }

    @StudentImplementationRequired("H5.4.3")
    @Override
    public void move() {
        if (battery.getCondition() == h05.equipment.EquipmentCondition.BROKEN) {
            return;
        }

        int oldX = getX();
        int oldY = getY();

        super.move();

        int newX = getX();
        int newY = getY();

        updateVision(oldX, oldY, newX, newY);

        int equipmentNumber = getNumberOfEquipments();
        battery.reduceDurability(equipmentNumber);
    }

    @DoNotTouch
    @Override
    public @NotNull GameSettings getGameSettings() {
        return settings;
    }

    @DoNotTouch
    @Override
    public Equipment[] getEquipments() {
        Equipment[] equipments = new Equipment[nextIndex];
        System.arraycopy(this.equipments, 0, equipments, 0, nextIndex);
        return equipments;
    }

    @DoNotTouch
    @Override
    public int getNumberOfEquipments() {
        return nextIndex + 2 + (tool == null ? 0 : 1);
    }

    @StudentImplementationRequired("H5.4.4")
    @Override
    public void use(int index) {
        int useableNumber = 0;
        h05.equipment.UsableEquipment targetEquipment = null;

        for (int i = 0; i < nextIndex; i++) {
            h05.equipment.UsableEquipment usable = settings.toUsableEquipment(equipments[i]);
            if (usable != null) {
                if (useableNumber == index) {
                    targetEquipment = usable;
                    break;
                }
                useableNumber++;
            }
        }

        if (targetEquipment != null) {
            targetEquipment.use(this);

            if (targetEquipment.getName().equals("TelephotoLens")) {
                int x = getX();
                int y = getY();
                updateVision(x, y, x, y);
            }

            settings.update();
        }
    }

    @DoNotTouch
    @Override
    public void equip(@NotNull Equipment equipment) {
        if (equipment.getName().equals("Battery")) {
            Battery newBattery = settings.toBattery(equipment);
            if (newBattery != null) {
                battery = newBattery;
                equipments[0] = newBattery;
            }
        } else if (equipment.getName().equals("Camera")) {
            Camera newCamera = settings.toCamera(equipment);
            if (newCamera != null) {
                camera = newCamera;
                equipments[1] = newCamera;
            }
        } else {
            for (int i = 2; i < nextIndex; i++) {
                if (equipments[i].getName().equals(equipment.getName())) {
                    equipments[i] = equipment;
                    return;
                }
            }
            if (equipment.isTool()) {
                tool = settings.toTool(equipment);
                return;
            }
            if (nextIndex + (tool == null ? -1 : 0) == equipments.length) {
                return;
            }
            equipments[nextIndex] = equipment;
            nextIndex++;
        }
    }

    @DoNotTouch
    @Override
    public void unequip(int index) {
        if (index + 2 < nextIndex) {
            return;
        }
        for (int i = index + 2; i < nextIndex - 1; i++) {
            equipments[i] = equipments[i + 1];
        }
        equipments[nextIndex - 1] = null;
        nextIndex--;
    }

    @DoNotTouch
    @Override
    public @NotNull Battery getBattery() {
        return battery;
    }

    @DoNotTouch
    @Override
    public @NotNull Camera getCamera() {
        return camera;
    }

    @DoNotTouch
    @Override
    public @Nullable Tool getTool() {
        return tool;
    }

    @DoNotTouch
    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    @StudentImplementationRequired("H5.4.1")
    @Override
    public void mine() {
        int currentX = getX();
        int currentY = getY();
        Direction dir = getDirection();

        int targetX = currentX;
        int targetY = currentY;

        if (dir == Direction.UP) {
            targetY++;
        } else if (dir == Direction.DOWN) {
            targetY--;
        } else if (dir == Direction.LEFT) {
            targetX--;
        } else if (dir == Direction.RIGHT) {
            targetX++;
        }

        int worldWidth = fopbot.World.getGlobalWorld().getWidth();
        int worldLength = fopbot.World.getGlobalWorld().getHeight();

        if (targetX < 0 || targetX >= worldWidth || targetY < 0 || targetY >= worldLength) {
            return;
        }

        h05.mineable.Mineable mineable = settings.getLootAt(targetX, targetY);

        if (mineable == null) {
            return;
        }

        boolean ifCompleted = mineable.onMined(tool);

        if (ifCompleted) {
            fopbot.World.getGlobalWorld().removeFieldEntity(targetX, targetY, h05.base.entity.Loot.class);

            boolean added = inventory.add(mineable);
            if (!added) {
                crash();
            }
        }
    }

    @DoNotTouch
    @Override
    public void pickGear() {
        int x = getX();
        int y = getY();
        Equipment equipment = settings.getAndRemoveGearAt(x, y);
        if (equipment != null) {
            Tool oldTool = tool;
            equip(equipment);
            if (equipment.isTool() && oldTool != null) {
                settings.placeGearAt(x, y, oldTool);
            }
        }
    }

    @DoNotTouch
    @Override
    public void handleKeyInput(
        @Nullable Direction direction,
        int selection,
        boolean isPickingGear,
        boolean isMining,
        boolean isInfo
    ) {
        if (direction != null) {
            while (getDirection() != direction) {
                turnLeft();
            }
            if (isFrontClear()) {
                move();
            }
        }
        if (selection != -1) {
            use(selection - 1);
        }
        if (isPickingGear) {
            pickGear();
        }
        if (isMining) {
            mine();
        }
        if (isInfo) {
            InfoPopup.showInfo(inventory);
        }
    }
}
