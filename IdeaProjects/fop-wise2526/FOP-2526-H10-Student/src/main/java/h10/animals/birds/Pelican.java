package h10.animals.birds;

import h10.abilities.Flies;
import h10.abilities.Walks;
import h10.animals.Animal;
import h10.animals.Location;

public class Pelican extends Animal implements Flies, Walks {

    /**
     * Constructs a pelican with the given name and age.
     * Default current location is GROUND.
     * A pelican has a mind of its own. What can't be reached by land will be accomplished through air.
     *
     * @param name the name of the animal
     * @param age  the age of the animal
     */
    public Pelican(String name, int age) {
        super(name, age, Location.WATER, Location.GROUND);
    }

    @Override
    public void flyTo(Location location) {
        setCurrentLocation(location);
        System.out.println(getName() + " has reached the " + location + ", ready to share the epic tale of Marlin's adventurous quest to find his son, Nemo!");
    }

    @Override
    public int getNumberOfLegs() {
        return 2;
    }

    @Override
    public void walkTo(Location location) {
        if (location != Location.AIR && location != Location.PLATFORM) {
            setCurrentLocation(location);
            System.out.println(getName() + "unwillingly walked to " + location + ".");
        } else {
            System.out.println(getName() + " is visibly annoyed and decides to simply fly.");
            flyTo(location);
        }
    }
}
