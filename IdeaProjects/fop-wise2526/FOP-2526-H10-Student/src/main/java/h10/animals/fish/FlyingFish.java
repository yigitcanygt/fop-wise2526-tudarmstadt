package h10.animals.fish;

import h10.abilities.Flies;
import h10.abilities.Swims;
import h10.animals.Animal;
import h10.animals.Location;

public class FlyingFish extends Animal implements Flies, Swims {
    private float elevation = Swims.MIN_ELEVATION;

    /**
     * Constructs an Animal with the given name and age.
     *
     * @param name the name of the animal
     * @param age  the age of the animal
     */
    public FlyingFish(String name, int age) {
        super(name, age, Location.WATER, Location.WATER);
    }

    @Override
    public void flyTo(Location location) {
        if (elevation != Swims.MAX_ELEVATION) {
            System.out.println(getName() + " is too deep under the water!");
            return;
        }

        setCurrentLocation(location);
        if (getCurrentLocation() == Location.WATER || getCurrentLocation() == Location.AIR) {
            System.out.println(getName() + " has reached " + location);
        } else {
            System.out.println(getName() + " has landed outside of water and gasps for oxygen!");
        }
    }

    @Override
    public float getElevation() {
        return elevation;
    }

    @Override
    public void swimUp() {
        if (getCurrentLocation() != Location.WATER) {
            System.out.println(getName() + " is flapping around, wondering why there is no water!");
        } else {
            elevation += 2;
            if (elevation > Swims.MAX_ELEVATION) {
                elevation = Swims.MAX_ELEVATION;
            }
        }
    }

    @Override
    public void swimDown() {
        if (getCurrentLocation() != Location.WATER) {
            System.out.println(getName() + " is flapping around on land, wondering why there is no water!");
        } else {
            elevation -= 2;
            if (elevation < Swims.MIN_ELEVATION) elevation = Swims.MIN_ELEVATION;
        }
    }
}
