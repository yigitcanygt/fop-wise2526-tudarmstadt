package h05.base.game;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.io.PropertyUtils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Defines constants for the game configuration.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public final class GameConstants {

    /**
     * The properties file containing game configuration settings.
     */
    @DoNotTouch
    private static final @NotNull String PROPERTIES_FILE = "h05.properties";

    /**
     * The width and height of the game world.
     */
    @DoNotTouch
    public static final int WORLD_WIDTH = PropertyUtils.getIntProperty(PROPERTIES_FILE, "WORLD_WIDTH");

    /**
     * The height of the game world.
     */
    @DoNotTouch
    public static final int WORLD_HEIGHT = PropertyUtils.getIntProperty(PROPERTIES_FILE, "WORLD_HEIGHT");

    /**
     * The delay in ticks for game updates.
     */
    @DoNotTouch
    public static final int TICK_DELAY = PropertyUtils.getIntProperty(PROPERTIES_FILE, "TICK_DELAY");

    /**
     * The visibility of the fog in the game used for debugging purposes.
     */
    @DoNotTouch
    public static final boolean FOG_VISIBILITY = PropertyUtils.getBooleanProperty(PROPERTIES_FILE, "FOG_VISIBILITY");

    /**
     * The seed for the random number generator.
     */
    @DoNotTouch
    private static final long _RANDOM_SEED = PropertyUtils.getLongProperty(PROPERTIES_FILE, "RANDOM_SEED");

    /**
     * The seed for the random number generator.
     */
    @DoNotTouch
    private static final long RANDOM_SEED = _RANDOM_SEED == 0 ? ThreadLocalRandom.current().nextLong() : _RANDOM_SEED;

    /**
     * The random number generator used throughout the game.
     */
    @DoNotTouch
    public static @NotNull Random RANDOM_GENERATOR = new Random(RANDOM_SEED);

    /**
     * Don't let anyone instantiate this class.
     */
    @DoNotTouch
    private GameConstants() {
    }

    /**
     * Returns a string representation of the game configuration.
     *
     * @return a string containing the game configuration details
     */
    @DoNotTouch
    public static @NotNull String getGameConfigurationString() {
        return """
            World width: %d
            World height: %d
            Tick delay: %d
            Random seed: %d
            """.formatted(WORLD_WIDTH, WORLD_HEIGHT, TICK_DELAY, RANDOM_SEED);
    }
}
