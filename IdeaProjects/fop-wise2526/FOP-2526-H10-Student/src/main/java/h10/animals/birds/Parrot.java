package h10.animals.birds;

import h10.abilities.Flies;
import h10.abilities.Walks;
import h10.animals.Animal;
import h10.animals.Location;

public class Parrot extends Animal implements Flies, Walks {

    /**
     * Constructs a parrot with the given name and age.
     * Default current location is GROUND.
     *
     * @param name the name of the animal
     * @param age  the age of the animal
     */
    public Parrot(String name, int age) {
        super(name, age, Location.PLATFORM, Location.GROUND);
    }

    @Override
    public void flyTo(Location location) {
        setCurrentLocation(location);
        System.out.println(getName() + " has reached the " + location + ", ready to squawk out the latest gossip and show off its colorful feathers!");
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
            System.out.println(getName() + "looks confusedly in the air and asks: can't I just fly?");
        }
    }
}
