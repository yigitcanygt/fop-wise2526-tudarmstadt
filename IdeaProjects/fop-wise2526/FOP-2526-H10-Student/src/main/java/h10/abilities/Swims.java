package h10.abilities;

/**
 * An object of a class implementing {@link Swims} has the ability to swim
 */
public interface Swims {
    /**
     * @return the elevation of the swimming animal
     */
    float getElevation();

    /**
     * Lets the animal swim up and increase in elevation.
     */
    void swimUp();

    /**
     * Lets the animal swim down and decrease in elevation.
     */
    void swimDown();

    /**
     * The max elevation an animal can swim to.
     */
    float MAX_ELEVATION = 0;

    /**
     * The min elevation an animal can swim to.
     */
    float MIN_ELEVATION = -10;

    /**
     * The elevation above which an animal is considered at a high elevation or at the surface.
     */
    float HIGH_ELEVATION = -3;
}
