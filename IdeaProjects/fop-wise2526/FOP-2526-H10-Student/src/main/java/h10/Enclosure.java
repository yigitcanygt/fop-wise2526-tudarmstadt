package h10;

import h10.abilities.Flies;
import h10.abilities.Walks;
import h10.animals.Animal;
import h10.animals.Location;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.function.*;
import java.util.stream.Stream;

/**
 * An object of a class implementing {@link Enclosure} has the ability to contain and manage a stack of {@link Animal}s.
 */
public interface Enclosure<A extends Animal> {

    /**
     * @return the stack of animals which is used manage the contained {@link Animal}s
     */
    @StudentImplementationRequired("H10.2.1")
    StackOfObjects<A> getAnimals();

    /**
     * Returns the location currently inhabited by the highest number of animals.
     *
     * @return the location with most animals
     */
    @StudentImplementationRequired("H10.2.1")
    default Location getMostCrowdedLocation() {
        int groundCounter = 0;
        int airCounter = 0;
        int platformCounter = 0;
        int waterCounter = 0;
        for (int i = 0; i < getAnimals().size(); i++) {
            Animal animal = (Animal) getAnimals().get(i);
            Location location = animal.getCurrentLocation();
            if (location == Location.GROUND) {
                groundCounter++;
            } else if (location == Location.AIR) {
                airCounter++;
            } else if (location == Location.PLATFORM) {
                platformCounter++;
            } else if (location == Location.WATER) {
                waterCounter++;
            }
        }
        Location mostCrowded = Location.GROUND;
        int maxCounter = groundCounter;
        if (airCounter > maxCounter) {
            maxCounter = airCounter;
            mostCrowded = Location.AIR;
        }
        if (platformCounter > maxCounter) {
            maxCounter = platformCounter;
            mostCrowded = Location.PLATFORM;
        }
        if (waterCounter > maxCounter) {
            mostCrowded = Location.WATER;
        }
        return mostCrowded;
    }

    /**
     * Feeds all contained animals.
     */
    @DoNotTouch
    void feed();

    /**
     * Counts the number of hungry {@link Animal}s in the enclosure.
     *
     * @return number of hungry {@link Animal}s in the enclosure
     */
    @SuppressWarnings("RedundantCast")
    @DoNotTouch
    default int countHungry() {
        int count = 0;
        for (int i = 0; i < this.getAnimals().size(); i++)
            if (((Animal) this.getAnimals().get(i)).isHungry()) count++;
        return count;
    }

    /**
     * Returns whether any elements of this enclosure match the provided predicate. May not evaluate the predicate on all
     * elements if not necessary for determining the result. If the enclosure is empty then false is returned and the
     * predicate is not evaluated.
     * <p>(This method is modelled after {@link Stream#anyMatch(Predicate)}.)</p>
     *
     * @param predicate a predicate to apply to elements of this enclosure
     * @return {@code true} if any elements of the stream match the provided predicate, otherwise {@code false}
     */
    @StudentImplementationRequired("H10.3.1") // TODO: H10.3.1
    default boolean anyMatch(Predicate<? super A> predicate) {
        for (int i = 0; i < this.getAnimals().size(); i++) {
            A animal = this.getAnimals().get(i);
            if (predicate.test(animal)) return true;
        }
        return false;
    }

    /**
     * Applies the given function to the animals of this enclosure.
     * <p>
     * Each animal is passed to the function, and the result of the function is used to replace the animal in the enclosure.
     * Because this method changes the animals in-place, as it follows the object-oriented programming paradigm, the
     * mapped animals must be of the same type as the original animals.
     *
     * @param mapper a mapping function to apply to each animal
     */
    @StudentImplementationRequired("H10.3.2") // TODO: H10.3.2
    default void mapObjectOriented(Function<? super A, ? extends A> mapper) {
        for (int i = 0; i < this.getAnimals().size(); i++) {
            A animal = getAnimals().get(i);
            A transformedAnimal = mapper.apply(animal);
            getAnimals().set(i, transformedAnimal);
        }
    }

    /**
     * Reduces the elements of this enclosure using the provided identity value and an accumulator function.
     * The accumulator function is applied to each element of the enclosure, accumulating the result.
     * <p>(This method is modelled after {@link Stream#reduce(Object, BiFunction, BinaryOperator)}}.)</p>
     * <p>
     * {@code reduce} is the java equivalent of the racket function {@code foldl}.
     *
     * @param identity    an initial value for the reduction
     * @param accumulator a function to combine the current accumulated value with an element of the enclosure
     * @param <R>         type of the result of the reduction
     * @return the result of the reduction, which is the accumulated value after processing all elements
     */
    @StudentImplementationRequired("H10.3.3") // TODO: H10.3.3
    default <R> R reduce(R identity, BiFunction<R, ? super A, R> accumulator) {
        R result = identity;
        for (int i = 0; i < this.getAnimals().size(); i++)
            result = accumulator.apply(result, getAnimals().get(i));
        return result;
    }

    /**
     * Maps the contained animals using the provided mapper function and returns a new enclosure of the specified type.
     * The new enclosure is created using the provided supplier.
     * <p>(This method is modelled after {@link Stream#map(Function)}.)</p>
     *
     * @param supp   a supplier to create the to be returned enclosure
     * @param mapper a mapping function to apply to each animal
     * @param <B>    type of the animals in the new enclosure
     * @param <E>    type of the new enclosure
     * @return a new enclosure containing the mapped animals
     */
    @StudentImplementationRequired("H10.3.4") // TODO: H10.3.4
    default <B extends Animal, E extends Enclosure<B>> E mapFunctional(Supplier<E> supp, Function<? super A, ? extends B> mapper) {
        E newGehege = supp.get();
        for (int i = 0; i < this.getAnimals().size(); i++) {
            A oldAnimal = this.getAnimals().get(i);
            B newAnimal = mapper.apply(oldAnimal);
            newGehege.getAnimals().push(newAnimal);
        }
        return newGehege;
    }

    /**
     * {@link Predicate} which returns true if an {@link Animal} is old (that means older than 10).
     */
    @DoNotTouch
    Predicate<Animal> IS_OLD = animal -> animal.getAge() > 10;

    /**
     * A {@link Predicate} which checks whether an {@link Animal} has exactly 2 legs.
     */
    @StudentImplementationRequired("H10.4.1") // TODO: H10.4.1
    Predicate<Walks> HAS_2_LEGS = animal -> animal.getNumberOfLegs() == 2;

    /**
     * {@link BiFunction} which signs a text for an {@link Animal} by appending the animal's name and age.
     * The sign is formatted as follows:
     * <pre>
     * text
     * animalName (animalAge years old)
     * </pre>
     */
    @StudentImplementationRequired("H10.4.2") // TODO: H10.4.2
    BiFunction <String, Animal, String> SIGN_WITH_ANIMAL_NAME = (text, animal) -> text + "\n" + animal.getName() + " (" + animal.getAge() + " years old)";

    /**
     * Flies the given {@link Animal} to its feeding location and lets it eat.
     *
     * @param animal the {@link Animal} which should fly and eat
     * @param <F>    Type of the {@link Animal} to fly and eat
     */
    @DoNotTouch
    static <F extends Animal & Flies> void flyAndEat(F animal) {
        animal.flyTo(animal.getFeedingLocation());
        animal.eat();
    }

    /**
     * Returns a {@link Consumer} which lets the consumed {@link Animal} which {@link Flies} eat and fly to its feeding location.
     *
     * @param <F> Type of the {@link Animal} to fly and eat
     * @return a {@link Consumer} which lets the consumed {@link Animal} eat and fly to its feeding location
     */
    @StudentImplementationRequired("H10.4.3")
    static <F extends Animal & Flies> Consumer<F> flyAndEat() {
        return animal -> {
            animal.flyTo(animal.getFeedingLocation());
            animal.eat();
    };
}
}
