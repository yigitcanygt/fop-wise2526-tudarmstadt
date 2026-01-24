package h11;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.function.BiFunction;

/**
 * An iterator-based implementation of a self-organizing list that moves accessed elements to a random position in the
 * list based on a random index which is smaller than the position of the accessed element.
 *
 * @param <T> the type of elements in the list
 * @author Nhan Huynh
 */
@DoNotTouch
public class RandomListIterator<T> extends RandomList<T> implements SelfOrganizingList<T> {

    /**
     * Creates a new list with the given elements and randomizer function.
     *
     * @param elements   the elements to be added to the list
     * @param randomizer the randomizer function used to generate random indices
     */
    @DoNotTouch
    public RandomListIterator(@NotNull T[] elements, @NotNull BiFunction<Integer, Integer, Integer> randomizer) {
        super(elements, randomizer);
    }

    /**
     * Creates a new list with the given elements and a default randomizer function.
     *
     * @param elements the elements to be added to the list
     */
    @DoNotTouch
    public RandomListIterator(@NotNull T[] elements) {
        super(elements);
    }

    /**
     * Creates a new empty list with the given randomizer function.
     *
     * @param randomizer the randomizer function used to generate random indices
     */
    @DoNotTouch
    public RandomListIterator(@NotNull BiFunction<Integer, Integer, Integer> randomizer) {
        super(randomizer);
    }

    /**
     * Creates a new empty list with a default randomizer function.
     */
    @DoNotTouch
    public RandomListIterator() {
    }

    @StudentImplementationRequired("H11.4.4")
    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        BidirectionalIterator<T> myIterator = iterator();
        T valueResult = null;
        int locationCounter = 0;
        while (myIterator.hasNext() && locationCounter <= index) {
            valueResult = myIterator.next();
            locationCounter++;
        }
        if (index == 0) {
            return valueResult;
        }
        int randomLocation = getRandomIndex(index);
        myIterator.remove();
        BidirectionalIterator<T> placeIter = iterator();
        int locCounter = 0;
        while (placeIter.hasNext() && locCounter < randomLocation) {
            placeIter.next();
            locCounter++;
        }
        placeIter.add(valueResult);
        return valueResult;
    }
}
