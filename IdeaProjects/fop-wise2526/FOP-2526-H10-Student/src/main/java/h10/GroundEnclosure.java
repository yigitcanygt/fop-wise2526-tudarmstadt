package h10;

import h10.abilities.Walks;
import h10.animals.Animal;

/**
 * An object of a class implementing {@link GroundEnclosure} has the ability to contain and manage a stack of
 * {@link Animal}s which have the ability to {@link Walks}.
 */
public class GroundEnclosure<A extends Animal & Walks> implements Enclosure<A> {
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
            a.eat();
        }
    }

    /**
     * Counts the total number of legs of all {@link Animal}s in the enclosure.
     *
     * @return the total number of legs of all {@link Animal}s in the enclosure
     */
    public int countLegs() {
        int sum = 0;
        for (int i = 0; i < this.getAnimals().size(); i++)
            sum += ((A) this.getAnimals().get(i)).getNumberOfLegs();
        return sum;
    }

    /**
     * Returns whether any {@link Animal} in the enclosure has exactly n legs.
     *
     * @param n the number of legs to check for
     * @return true if there is at least one {@link Animal} with exactly n legs, false otherwise
     */
    public boolean hasAnyAnimalWithNLegs(int n) {
        for (int i = 0; i < this.getAnimals().size(); i++) {
            if (((A) this.getAnimals().get(i)).getNumberOfLegs() == n) {
                return true;
            }
        }
        return false;
    }
}
