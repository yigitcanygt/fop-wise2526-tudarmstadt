package h10.abilities;

import h10.animals.Location;

/**
 * An object of a class implementing {@link Flies} has the ability to fly
 */
public interface Flies {

    /**
     * Lets the animal fly to any location.
     *
     * @param location the destination
     */
    void flyTo(Location location);
}
