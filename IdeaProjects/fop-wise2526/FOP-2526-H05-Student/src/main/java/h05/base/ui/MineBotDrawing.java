package h05.base.ui;

import fopbot.ColorProfile;
import fopbot.Drawable;
import fopbot.DrawingContext;
import fopbot.PaintUtils;
import h05.entity.MineBot;
import h05.equipment.Equipment;
import h05.equipment.Tool;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

@DoNotTouch
public class MineBotDrawing implements Drawable<MineBot> {

    @DoNotTouch
    private static final double EQUIPMENT_SIZE_SCALE = 15d;

    @DoNotTouch
    @Override
    public void draw(Graphics g, DrawingContext<? extends MineBot> context) {
        final MineBot robot = context.entity();

        if (robot.isTurnedOff() && !context.world().isDrawTurnedOffRobots()) {
            return;
        }

        final ColorProfile profile = context.colorProfile();
        final Point upperLeft = context.upperLeftCorner();
        final int offset = profile.fieldInnerOffset();
        final int robotSize = scale(profile.fieldInnerSize() - offset * 2, context);
        final int scaledX = scale(upperLeft.x, context);
        final int scaledY = scale(upperLeft.y, context);
        final int rotationDegrees = robot.getDirection().ordinal() * 90;
        final Image robotImage = robot.getRobotFamily().render(
            robotSize,
            rotationDegrees,
            robot.isTurnedOff() || robot.isBatteryBroken()
        );

        g.drawImage(
            robotImage,
            scale(upperLeft.x, context),
            scale(upperLeft.y, context),
            null
        );

        // Draw overlay battery
        final Graphics2D g2d = (Graphics2D) g;
        final double equipmentSize = scale(20d, context);
        double x;
        double y;
        y = switch (robot.getDirection()) {
            case UP, RIGHT -> {
                x = scaledX;
                yield scaledY + robotSize - equipmentSize;
            }
            case DOWN, LEFT -> {
                x = scaledX + robotSize - equipmentSize;
                yield scaledY;
            }
        };
        final Image batteryImage = loadEquipmentImage(robot.getBattery(), context, rotationDegrees - 90);
        g2d.drawImage(batteryImage, (int) x, (int) y, (int) equipmentSize, (int) equipmentSize, null);

        // Draw overlay tool
        x = switch (robot.getDirection()) {
            case UP, RIGHT -> {
                y = scaledY;
                yield scaledX + robotSize - equipmentSize;
            }
            case DOWN, LEFT -> {
                y = scaledY + robotSize - equipmentSize;
                yield scaledX;
            }
        };
        final Tool tool = robot.getTool();
        if (tool != null) {
            final Image toolImage = loadEquipmentImage(tool, context, 0);
            g2d.drawImage(toolImage, (int) x, (int) y, (int) equipmentSize, (int) equipmentSize, null);
        }
    }

    /**
     * Loads the image for the given equipment, applying the specified rotation and scaling it to the target size.
     *
     * @param equipment       the equipment to load the image for
     * @param context         the drawing context containing the scale and other properties
     * @param rotationDegrees the rotation in degrees to apply to the image
     * @return the loaded and scaled image of the equipment
     */
    @DoNotTouch
    private Image loadEquipmentImage(Equipment equipment, DrawingContext<? extends MineBot> context, int rotationDegrees) {
        final String path = equipment.getName().toLowerCase()
            + (equipment instanceof Tool ? "" : "_" + equipment.getCondition().name().toLowerCase()) + ".svg";
        return PaintUtils.loadFieldImage(
            getClass().getResourceAsStream(path),
            rotationDegrees,
            (int) scale(EQUIPMENT_SIZE_SCALE, context)
        );
    }
}
