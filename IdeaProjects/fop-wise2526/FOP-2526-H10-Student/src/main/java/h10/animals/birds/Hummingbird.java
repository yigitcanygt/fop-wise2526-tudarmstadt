package h10.animals.birds;

import h10.abilities.Flies;
import h10.abilities.Walks;
import h10.animals.Animal;
import h10.animals.Location;

public class Hummingbird extends Animal implements Flies, Walks {

    /**
     * Constructs a hummingbird with the given name and age.
     * Default current location is GROUND.
     * A hummingbird is lazy and strictly refuses to walk.
     *
     * @param name the name of the animal
     * @param age  the age of the animal
     */
    public Hummingbird(String name, int age) {
        super(name, age, Location.PLATFORM, Location.GROUND);
    }

    @Override
    public void flyTo(Location location) {
        setCurrentLocation(location);
        System.out.println(getName() + " has reached the " + location + ", excited to sip some sweet nectar!");
    }

    @Override
    public int getNumberOfLegs() {
        return 2;
    }

    @Override
    public void walkTo(Location location) {
        System.out.println(getName() + " refuses to walk and wants you to know that he has wings.");
    }
}
