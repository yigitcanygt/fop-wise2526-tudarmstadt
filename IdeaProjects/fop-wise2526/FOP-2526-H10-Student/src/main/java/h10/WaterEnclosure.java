package h10;

import h10.abilities.Swims;
import h10.animals.Animal;

/**
 * An object of a class implementing {@link WaterEnclosure} has the ability to contain and manage a stack of
 * {@link Animal}s which have the ability to {@link Swims}.
 */
public class WaterEnclosure<A extends Animal & Swims> implements Enclosure<A> {
    /**
     * The stack of animals which is used manage the contained Animals.
     */
    final StackOfObjects<A> animals = new StackOfObjects<>();

    @Override
    public StackOfObjects<A> getAnimals() {
        return animals;
    }

    @Override
    public void feed() {
        for (int i = 0; i < this.getAnimals().size(); i++) {
            A a = (A) this.getAnimals().get(i);
            if (a.isHungry()) {
                if (a.getElevation() < Swims.HIGH_ELEVATION) a.swimUp();
                a.eat();
                a.swimDown();
            }
        }
    }

    /**
     * Compares the elevations of all {@link Animal}s in the enclosure and returns the mean.
     *
     * @return the mean elevation of all {@link Animal}s in the enclosure
     */
    public float getMeanElevation() {
        float sum = 0;
        for (int i = 0; i < this.getAnimals().size(); i++) {
            A a = (A) this.getAnimals().get(i);
            sum += a.getElevation();
        }
        return sum / this.getAnimals().size();
    }
}
