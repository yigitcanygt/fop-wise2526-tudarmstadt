package h10.abilities;

import h10.animals.Location;

/**
 * An object of a class implementing {@link Walks} has the ability to walk
 */
public interface Walks {
    /**
     * @return the number of legs of the animal
     */
    int getNumberOfLegs();

    /**
     * lets animal travel to a location that can be reached on foot.
     *
     * @param location the new location
     */
    void walkTo(Location location);
}
