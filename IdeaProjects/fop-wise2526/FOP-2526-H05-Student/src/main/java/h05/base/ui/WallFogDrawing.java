package h05.base.ui;

import fopbot.ColorProfile;
import fopbot.Drawable;
import fopbot.DrawingContext;
import fopbot.KarelWorld;
import fopbot.Wall;
import h05.base.entity.Fog;
import h05.base.game.GameConstants;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

@DoNotTouch
public class WallFogDrawing implements Drawable<Wall> {

    @DoNotTouch
    public WallFogDrawing() {
    }

    @DoNotTouch
    @Override
    public void draw(Graphics g, DrawingContext<? extends Wall> context) {
        final Wall entity = context.entity();
        final KarelWorld world = context.world();
        final ColorProfile profile = context.colorProfile();
        final Point upperLeft = context.upperLeftCorner();

        final int x = entity.getX();
        final int y = entity.getY();
        final boolean isHorizontal = entity.isHorizontal();
        final boolean isFogHere = context.field().containsEntity(Fog.class);
        final boolean isFogInNextField = isHorizontal
            ? (y + 1 < world.getHeight() && world.getField(x, y + 1).containsEntity(Fog.class))
            : (x + 1 < world.getWidth() && world.getField(x + 1, y).containsEntity(Fog.class));

        if (GameConstants.FOG_VISIBILITY && isFogHere && isFogInNextField) {
            return;
        }

        final Color oldColor = g.getColor();
        g.setColor(profile.getWallColor());

        final int offset = profile.fieldInnerOffset();
        final int thickness = profile.fieldBorderThickness();
        final int innerSize = profile.fieldInnerSize();
        final int drawX;
        final int drawY;
        final int width;
        final int height;
        if (isHorizontal) {
            drawX = upperLeft.x - offset * 2;
            drawY = upperLeft.y - offset - thickness;
            width = innerSize + offset * 2;
            height = thickness;
        } else {
            drawX = upperLeft.x - offset + innerSize;
            drawY = upperLeft.y - offset * 2;
            width = thickness;
            height = innerSize + offset * 2;
        }
        g.fillRect(
            scale(drawX, context),
            scale(drawY, context),
            scale(width, context),
            scale(height, context)
        );

        g.setColor(oldColor);
    }
}
