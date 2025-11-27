package h05.base.ui;

import fopbot.DrawingContext;
import fopbot.PaintUtils;
import fopbot.SvgBasedDrawing;
import fopbot.World;
import h05.base.entity.Fog;
import h05.base.game.GameConstants;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.Graphics;
import java.awt.Image;

/**
 * Specifies the drawing of the {@link Fog} entity.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public class FogDrawing extends SvgBasedDrawing<Fog> {

    /**
     * The dark mode and light mode types for the fog drawing.
     */
    @DoNotTouch
    private static final String[] TYPES = {"ln", "dm"};

    /**
     * Constructs a new {@link FogDrawing} instance.
     */
    @DoNotTouch
    public FogDrawing() {
        super(TYPES.length);
    }

    @DoNotTouch
    @Override
    protected Image getCurrentDrawingImage(Fog entity) {
        return World.getGlobalWorld().getGuiPanel().isDarkMode() ? getImage(1) : getImage(0);
    }

    @DoNotTouch
    @Override
    protected void loadImages(int targetSize, DrawingContext<? extends Fog> context) {
        final Fog entity = context.entity();
        final String suffix = entity.getClass().getSimpleName().toLowerCase();
        for (int i = 0; i < TYPES.length; i++) {
            final String type = TYPES[i];
            final String path = suffix + "_" + type + EXTENSION;
            final Image image = PaintUtils.loadFieldImage(
                getClass().getResourceAsStream(path),
                0,
                targetSize
            );
            setImage(i, image);
        }
    }

    @DoNotTouch
    @Override
    public void draw(Graphics g, DrawingContext<? extends Fog> context) {
        if (!GameConstants.FOG_VISIBILITY) {
            return;
        }
        super.draw(g, context);
    }
}
