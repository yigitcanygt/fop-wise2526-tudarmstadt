package h11;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;

/**
 * A random implementation of a self-organizing list that moves accessed elements to the front of the list based on a
 * random index which is smaller than the position of the accessed element.
 *
 * @param <T> the type of elements in the list
 * @author Nhan Huynh
 */
@DoNotTouch
public abstract class RandomList<T> extends AbstractSelfOrganizingList<T> implements SelfOrganizingList<T> {

    /**
     * The randomizer function used to generate random indices.
     */
    @DoNotTouch
    private final BiFunction<Integer, Integer, Integer> randomizer;

    /**
     * Creates a new list with the given elements and randomizer function.
     *
     * @param elements   the elements to be added to the list
     * @param randomizer the randomizer function used to generate random indices
     */
    @DoNotTouch
    public RandomList(@NotNull T[] elements, @Nullable BiFunction<Integer, Integer, Integer> randomizer) {
        super(elements);
        this.randomizer = randomizer;
    }

    /**
     * Creates a new empty list with the given randomizer function.
     *
     * @param randomizer the randomizer function used to generate random indices
     */
    @DoNotTouch
    @SuppressWarnings("unchecked")
    public RandomList(@NotNull BiFunction<Integer, Integer, Integer> randomizer) {
        this((T[]) new Object[0], randomizer);
    }

    /**
     * Creates a new list with the given elements and a default randomizer function.
     *
     * @param elements the elements to be added to the list
     */
    @DoNotTouch
    public RandomList(@NotNull T[] elements) {
        this(elements, (min, max) -> ThreadLocalRandom.current().nextInt(min, max));
    }

    /**
     * Creates a new empty list with a default randomizer function.
     */
    @DoNotTouch
    public RandomList() {
        this((min, max) -> ThreadLocalRandom.current().nextInt(min, max));
    }

    /**
     * Generates a random index between 0 (inclusive) and max (exclusive).
     *
     * @param max the upper bound for the random index
     * @return a random index between 0 (inclusive) and max (exclusive)
     */
    @DoNotTouch
    protected int getRandomIndex(int max) {
        return randomizer.apply(0, max);
    }

    @DoNotTouch
    @Override
    public @NotNull Strategy strategy() {
        return Strategy.RANDOM;
    }
}
