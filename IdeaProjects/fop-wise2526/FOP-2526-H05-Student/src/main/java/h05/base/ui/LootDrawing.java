package h05.base.ui;

import fopbot.DrawingContext;
import fopbot.PaintUtils;
import fopbot.SvgBasedDrawing;
import h05.base.entity.Loot;
import h05.mineable.Mineable;
import h05.mineable.MiningProgress;
import h05.mineable.Rock;
import h05.mineable.Tree;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.Image;
import java.util.List;

@DoNotTouch
public class LootDrawing extends SvgBasedDrawing<Loot> {

    @DoNotTouch
    public static final List<Class<? extends Mineable>> AVAILABLE_MINEABLES = List.of(
        Tree.class,
        Rock.class
    );

    @DoNotTouch
    public LootDrawing() {
        super(AVAILABLE_MINEABLES.size() * MiningProgress.values().length);
    }

    @DoNotTouch
    @Override
    protected Image getCurrentDrawingImage(Loot entity) {
        Mineable mineable = entity.getMineable();
        Class<? extends Mineable> clazz = mineable.getClass();
        return getImage(
            AVAILABLE_MINEABLES.indexOf(clazz)
                * MiningProgress.values().length
                + mineable.getProgress().ordinal()
        );
    }

    @DoNotTouch
    @Override
    protected void loadImages(int targetSize, DrawingContext<? extends Loot> context) {
        MiningProgress[] progresses = MiningProgress.values();
        for (Class<? extends Mineable> clazz : AVAILABLE_MINEABLES) {
            for (MiningProgress state : progresses) {
                String path = clazz.getSimpleName().toLowerCase() + "_" + state.name().toLowerCase() + EXTENSION;
                Image image = PaintUtils.loadFieldImage(
                    getClass().getResourceAsStream(path),
                    0,
                    targetSize
                );
                setImage(AVAILABLE_MINEABLES.indexOf(clazz) * progresses.length + state.ordinal(), image);
            }
        }
    }
}
