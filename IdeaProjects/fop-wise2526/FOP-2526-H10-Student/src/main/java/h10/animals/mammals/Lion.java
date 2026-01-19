package h10.animals.mammals;

import h10.abilities.Walks;
import h10.animals.Animal;
import h10.animals.Location;

/**
 * An object of a class {@link Lion} represents a lion which can walk.
 */
public class Lion extends Animal implements Walks {
    /**
     * Constructs a Lion with the given name and age.
     * Default location is GROUND.
     *
     * @param name the name of the animal
     * @param age  the age of the animal
     */
    public Lion(String name, int age) {
        super(name, age, Location.GROUND, Location.GROUND);
    }

    @Override
    public int getNumberOfLegs() {
        return 4;
    }

    @Override
    public void walkTo(Location location) {
        if (location != Location.AIR && location != Location.PLATFORM) {
            setCurrentLocation(location);
            System.out.println(getName() + "walked to " + location + ".");
        } else {
            System.out.println(getName() + " can't fly!");
        }
    }
}
