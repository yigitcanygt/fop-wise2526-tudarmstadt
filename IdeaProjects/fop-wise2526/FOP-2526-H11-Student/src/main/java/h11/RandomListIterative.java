package h11;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.function.BiFunction;

/**
 * An iterative implementation of a self-organizing list that moves accessed elements to a random position in the list
 * based on a random index which is smaller than the position of the accessed element.
 *
 * @param <T> the type of elements in the list
 * @author Nhan Huynh
 */
@DoNotTouch
public class RandomListIterative<T> extends RandomList<T> implements SelfOrganizingList<T> {

    /**
     * Creates a new list with the given elements and randomizer function.
     *
     * @param elements   the elements to be added to the list
     * @param randomizer the randomizer function used to generate random indices
     */
    @DoNotTouch
    public RandomListIterative(@NotNull T[] elements, @NotNull BiFunction<Integer, Integer, Integer> randomizer) {
        super(elements, randomizer);
    }

    /**
     * Creates a new empty list with the given randomizer function.
     *
     * @param randomizer the randomizer function used to generate random indices
     */
    @DoNotTouch
    public RandomListIterative(@NotNull BiFunction<Integer, Integer, Integer> randomizer) {
        super(randomizer);
    }

    /**
     * Creates a new list with the given elements and a default randomizer function.
     *
     * @param elements the elements to be added to the list
     */
    @DoNotTouch
    public RandomListIterative(@NotNull T[] elements) {
        super(elements);
    }

    /**
     * Creates a new empty list with a default randomizer function.
     */
    @DoNotTouch
    public RandomListIterative() {
    }

    @StudentImplementationRequired("H11.3")
    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        ListItem<T> currentElement = head;
        ListItem<T> firstPrev = null;
        int counter = 0;
        while (counter < index) {
            firstPrev = currentElement;
            currentElement = currentElement.next;
            counter++;
        }
        T valueResult = currentElement.key;
        if (index > 0) {
            int randomNumber = getRandomIndex(index);
            firstPrev.next = currentElement.next;
            if (currentElement.next == null) {
                tail = firstPrev;
            }
            ListItem<T> temporaryElement = head;
            ListItem<T> prevElement = null;
            int positionCounter = 0;
            while (positionCounter < randomNumber) {
                prevElement = temporaryElement;
                temporaryElement = temporaryElement.next;
                positionCounter++;
            }
            if (prevElement == null) {
                currentElement.next = head;
                head = currentElement;
            } else {
                currentElement.next = temporaryElement;
                prevElement.next = currentElement;
            }
        }
        return valueResult;
    }
}
