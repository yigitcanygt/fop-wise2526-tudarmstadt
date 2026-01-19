package h10.animals.birds;

import h10.abilities.Swims;
import h10.abilities.Walks;
import h10.animals.Animal;
import h10.animals.Location;

/**
 * An object of a class {@link Penguin} represents a penguin which can swim and walk.
 */
public class Penguin extends Animal implements Walks, Swims {

    /**
     * the elevation in water. Elevation is +1 if the animal is out of water.
     */
    float elevation = 1;

    /**
     * Constructs a Penguin with the given name and age.
     * Default current location is GROUND.
     *
     * @param name the name of the animal
     * @param age  the age of the animal
     */
    public Penguin(String name, int age) {
        super(name, age, Location.GROUND, Location.GROUND);
    }

    @Override
    public float getElevation() {
        return elevation;
    }

    @Override
    public void swimUp() {
        if (getCurrentLocation() != Location.WATER) {
            System.out.println(getName() + " is confused. He is outside of water!");
        } else {
            elevation = Swims.MAX_ELEVATION;
        }
    }

    @Override
    public void swimDown() {
        if (getCurrentLocation() != Location.WATER) {
            System.out.println(getName() + " is confused. He is outside of water!");
        } else {
            elevation -= 4;
            if (elevation < Swims.MIN_ELEVATION) elevation = Swims.MIN_ELEVATION;
        }
    }

    @Override
    public int getNumberOfLegs() {
        return 2;
    }

    @Override
    public void walkTo(Location location) {
        if (location != Location.AIR && location != Location.PLATFORM) {
            setCurrentLocation(location);
            System.out.println(getName() + "has reached " + location + ".");
        } else {
            System.out.println(getName() + " can't fly!");
        }
    }
}
