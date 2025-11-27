package h05.base.ui;

import fopbot.DrawingContext;
import fopbot.PaintUtils;
import fopbot.SvgBasedDrawing;
import h05.base.entity.Gear;
import h05.equipment.Equipment;
import h05.equipment.EquipmentCondition;
import h05.equipment.Tool;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.Image;
import java.util.List;

@DoNotTouch
public class GearDrawing extends SvgBasedDrawing<Gear> {

    /**
     * The available equipment types that can be used by the {@link Gear} entity.
     */
    @DoNotTouch
    public static final List<String> AVAILABLE_EQUIPMENTS = List.of(
        "Battery",
        "Camera",
        "Powerbank",
        "TelephotoLens",
        "WallBreaker"
    );

    /**
     * The available tool types that can be used by the {@link Gear} entity.
     */
    @DoNotTouch
    public static final List<String> AVAILABLE_TOOLS = List.of(
        "Axe",
        "Pickaxe"
    );

    /**
     * Constructs a new {@link GearDrawing} instance.
     */
    @DoNotTouch
    public GearDrawing() {
        super(AVAILABLE_EQUIPMENTS.size() * EquipmentCondition.values().length + AVAILABLE_TOOLS.size());
    }

    @DoNotTouch
    @Override
    protected Image getCurrentDrawingImage(Gear entity) {
        final Equipment equipment = entity.getEquipment();
        final int index;
        final int numberOfConditions = EquipmentCondition.values().length;
        if (equipment instanceof Tool) {
            index = AVAILABLE_EQUIPMENTS.size() * numberOfConditions + AVAILABLE_TOOLS.indexOf(equipment.getClass().getSimpleName());
        } else {
            index = AVAILABLE_EQUIPMENTS.indexOf(equipment.getClass().getSimpleName()) * numberOfConditions + equipment.getCondition().ordinal();
        }
        return getImage(index);
    }

    @DoNotTouch
    @Override
    protected void loadImages(int targetSize, DrawingContext<? extends Gear> context) {
        final EquipmentCondition[] conditions = EquipmentCondition.values();
        for (String name : AVAILABLE_EQUIPMENTS) {
            for (EquipmentCondition condition : conditions) {
                final String path = name.toLowerCase() + "_" + condition.name().toLowerCase() + EXTENSION;
                final Image image = PaintUtils.loadFieldImage(
                    getClass().getResourceAsStream(path),
                    0,
                    targetSize
                );
                setImage(AVAILABLE_EQUIPMENTS.indexOf(name) * conditions.length + condition.ordinal(), image);
            }
        }
        final int offset = AVAILABLE_EQUIPMENTS.size() * conditions.length;
        for (String name : AVAILABLE_TOOLS) {
            final String path = name.toLowerCase() + EXTENSION;
            final Image image = PaintUtils.loadFieldImage(
                getClass().getResourceAsStream(path),
                0,
                targetSize
            );
            setImage(offset + AVAILABLE_TOOLS.indexOf(name), image);
        }
    }
}
