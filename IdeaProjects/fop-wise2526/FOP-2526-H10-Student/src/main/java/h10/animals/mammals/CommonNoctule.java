package h10.animals.mammals;

import h10.abilities.Flies;
import h10.abilities.Walks;
import h10.animals.Animal;
import h10.animals.Location;

public class CommonNoctule extends Animal implements Flies, Walks {
    /**
     * Constructs a Common Noctule (a bat) with the given name and age.
     * Default location is the platform.
     *
     * @param name the name of the bat
     * @param age  the age of the bat
     */
    public CommonNoctule(String name, int age) {
        super(name, age, Location.AIR, Location.PLATFORM);
    }

    @Override
    public void flyTo(Location location) {
        setCurrentLocation(location);
        System.out.println(getName() + " has reached the " + location + ".");
    }

    @Override
    public int getNumberOfLegs() {
        return 4;
    }

    @Override
    public void walkTo(Location location) {
        if (location != Location.AIR && location != Location.PLATFORM) {
            setCurrentLocation(location);
            System.out.println(getName() + "reached " + location + ".");
        } else {
            System.out.println(getName() + ": What do I have wings for? I can not reach this location by walking!");
        }
    }
}
