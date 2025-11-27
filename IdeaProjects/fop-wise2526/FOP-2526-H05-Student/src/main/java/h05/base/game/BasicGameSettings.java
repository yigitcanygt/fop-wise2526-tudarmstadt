package h05.base.game;

import fopbot.FieldEntity;
import fopbot.Wall;
import fopbot.World;
import h05.base.entity.Fog;
import h05.base.entity.Gear;
import h05.base.entity.Loot;
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

import java.util.ArrayList;
import java.util.List;

/**
 * A basic implementation of the GameSettings interface.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public final class BasicGameSettings implements GameSettings {

    /**
     * Constructs a new {@link BasicGameSettings} instance.
     */
    @DoNotTouch
    public BasicGameSettings() {
    }

    @DoNotTouch
    private static <E> @NotNull List<@NotNull E> getEntitiesAt(int x, int y, @NotNull Class<E> clazz) {
        List<E> entities = new ArrayList<>();
        for (FieldEntity entity : World.getGlobalWorld().getField(x, y).getEntities()) {
            if (clazz.isInstance(entity)) {
                entities.add(clazz.cast(entity));
            }
        }
        return entities;
    }

    @DoNotTouch
    private static <E> @Nullable E getEntityAt(int x, int y, Class<E> clazz) {
        List<E> entities = getEntitiesAt(x, y, clazz);
        return entities.isEmpty() ? null : entities.getFirst();
    }

    @DoNotTouch
    @Override
    public void placeFog(int x, int y) {
        if (getEntityAt(x, y, Fog.class) != null) {
            return;
        }
        World.getGlobalWorld().placeFieldEntity(new Fog(x, y));
    }

    @DoNotTouch
    @Override
    public void removeFog(int x, int y) {
        World.getGlobalWorld().removeFieldEntity(x, y, Fog.class);
    }

    @Override
    @DoNotTouch
    public @Nullable Battery toBattery(Equipment equipment) {
        return equipment instanceof Battery battery ? battery : null;
    }

    @Override
    @DoNotTouch
    public @Nullable Camera toCamera(Equipment equipment) {
        return equipment instanceof Camera camera ? camera : null;
    }

    @Override
    @DoNotTouch
    public @Nullable Tool toTool(Equipment equipment) {
        return equipment instanceof Tool tool ? tool : null;
    }

    @Override
    @DoNotTouch
    public @Nullable UsableEquipment toUsableEquipment(Equipment equipment) {
        return equipment instanceof UsableEquipment usableEquipment ? usableEquipment : null;
    }

    @DoNotTouch
    @Override
    public @Nullable Mineable getMineableAt(int x, int y) {
        Loot loot = getEntityAt(x, y, Loot.class);
        return loot != null ? loot.getMineable() : null;
    }

    @DoNotTouch
    @Override
    public @Nullable UsableEquipment getUsableEquipmentAt(int x, int y) {
        Gear gear = getEntityAt(x, y, Gear.class);
        return gear == null
            ? null : gear.getEquipment() instanceof UsableEquipment usableEquipment
            ? usableEquipment : null;
    }

    @Override
    @DoNotTouch
    public @Nullable Equipment getAndRemoveGearAt(int x, int y) {
        Gear gear = getEntityAt(x, y, Gear.class);
        if (gear != null) {
            removeEntity(gear);
            return gear.getEquipment();
        }
        return null;
    }

    @Override
    public void placeGearAt(int x, int y, Equipment equipment) {
        Gear gear = new Gear(x, y, equipment);
        World.getGlobalWorld().placeFieldEntity(gear);
    }

    @Override
    @DoNotTouch
    public @Nullable Mineable getLootAt(int x, int y) {
        Loot loot = getEntityAt(x, y, Loot.class);
        return loot != null ? loot.getMineable() : null;
    }

    @DoNotTouch
    @Override
    public @Nullable Miner getMinerAt(int x, int y) {
        return getEntityAt(x, y, Miner.class);
    }

    @DoNotTouch
    @Override
    public @NotNull Wall[] getWallsAt(int x, int y) {
        List<Wall> entities = getEntitiesAt(x, y, Wall.class);
        Wall[] walls = new Wall[entities.size()];
        int i = 0;
        for (Wall wall : entities) {
            walls[i] = wall;
            i++;
        }
        return walls;
    }

    @Override
    @DoNotTouch
    public void removeEntity(@NotNull FieldEntity entity) {
        World.getGlobalWorld().removeFieldEntity(entity);
    }

    @Override
    @DoNotTouch
    public void update() {
        World.getGlobalWorld().getGuiPanel().updateGui();
    }
}
