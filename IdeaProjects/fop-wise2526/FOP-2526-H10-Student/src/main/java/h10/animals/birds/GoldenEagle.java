package h10.animals.birds;

import h10.abilities.Flies;
import h10.abilities.Walks;
import h10.animals.Animal;
import h10.animals.Location;

public class GoldenEagle extends Animal implements Flies, Walks {

    /**
     * Constructs a golden eagle with the given name and age.
     * Default current location is GROUND.
     *
     * @param name the name of the animal
     * @param age  the age of the animal
     */
    public GoldenEagle(String name, int age) {
        super(name, age, Location.AIR, Location.GROUND);
    }

    @Override
    public void flyTo(Location location) {
        setCurrentLocation(location);
        System.out.println(getName() + " has reached the " + location + " with a graceful swoop!");
    }

    @Override
    public int getNumberOfLegs() {
        return 2;
    }

    @Override
    public void walkTo(Location location) {
        if (location != Location.AIR && location != Location.PLATFORM) {
            System.out.println("He ain't got wings for nothing! He sure ain't walking!");
            flyTo(location);
        } else {
            System.out.println(getName() + "can't reach the location by walking!");
        }

    }
}
