package h11;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.function.BiFunction;

/**
 * A recursive implementation of a self-organizing list that moves accessed elements to a random position in the list
 * based on a random index which is smaller than the position of the accessed element.
 *
 * @param <T> the type of elements in the list
 * @author Nhan Huynh
 */
@DoNotTouch
public class RandomListRecursive<T> extends RandomList<T> implements SelfOrganizingList<T> {

    /**
     * Creates a new list with the given elements and randomizer function.
     *
     * @param elements   the elements to be added to the list
     * @param randomizer the randomizer function used to generate random indices
     */
    @DoNotTouch
    public RandomListRecursive(@NotNull T[] elements, @NotNull BiFunction<Integer, Integer, Integer> randomizer) {
        super(elements, randomizer);
    }

    /**
     * Creates a new empty list with the given randomizer function.
     *
     * @param randomizer the randomizer function used to generate random indices
     */
    @DoNotTouch
    public RandomListRecursive(@NotNull BiFunction<Integer, Integer, Integer> randomizer) {
        super(randomizer);
    }

    /**
     * Creates a new list with the given elements and a default randomizer function.
     *
     * @param elements the elements to be added to the list
     */
    @DoNotTouch
    public RandomListRecursive(@NotNull T[] elements) {
        super(elements);
    }

    /**
     * Creates a new empty list with a default randomizer function.
     */
    @DoNotTouch
    public RandomListRecursive() {
    }

    @StudentImplementationRequired("H11.3")
    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        ListItem<T> searchedElement = findRecursive(head, index, 0);
        T resultValue = searchedElement.key;

        if (index > 0) {
            int randomPosition = getRandomIndex(index);
            recursiveLocationChange(null, null, head, index, randomPosition, 0);
        }
        return resultValue;
    }
    private ListItem<T> findRecursive(ListItem<T> item, int targetIndex, int activeIndex) {
        if (activeIndex == targetIndex) {
            return item;
        }
        return findRecursive(item.next, targetIndex, activeIndex + 1);
    }
    private void recursiveLocationChange(ListItem<T> prevPrev, ListItem<T> prev, ListItem<T> curr, int targetIndex, int randomIndex, int activeIndex) {
        if (activeIndex == targetIndex) {
            ListItem<T> moved = curr;
            prev.next = curr.next;

            if (curr.next == null) {
                tail = prev;
            }
            recursiveReplace(null, head, moved, randomIndex, 0);
            return;
        }
        recursiveLocationChange(prev, curr, curr.next, targetIndex, randomIndex, activeIndex + 1);
    }
    private void recursiveReplace(ListItem<T> prevItem, ListItem<T> currentItem, ListItem<T> llBeAdded, int targetPosition, int currentPosition) {
        if (currentPosition == targetPosition) {
            if (prevItem == null) {
                llBeAdded.next = head;
                head = llBeAdded;
            } else {
                llBeAdded.next = currentItem;
                prevItem.next = llBeAdded;
            }
            return;
        }
        recursiveReplace(currentItem, currentItem.next, llBeAdded, targetPosition, currentPosition + 1);
    }
}
