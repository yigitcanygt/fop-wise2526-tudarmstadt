package h11;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * A recursive implementation of a self-organizing list that moves accessed elements to the front of the list.
 *
 * @param <T> the type of elements in the list
 * @author Nhan Huynh
 */
@DoNotTouch
public class MoveToFrontListRecursive<T> extends MoveToFrontList<T> implements SelfOrganizingList<T> {

    /**
     * Creates a new empty list.
     */
    @DoNotTouch
    public MoveToFrontListRecursive() {
    }

    /**
     * Creates a list with the given elements.
     *
     * @param elements the elements to be added to the list
     */
    @DoNotTouch
    public MoveToFrontListRecursive(@NotNull T[] elements) {
        super(elements);
    }


    @StudentImplementationRequired("H11.1")
    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        ListItem<T> foundElement = findRecursive(head, index, 0);
        T value = foundElement.key;

        if (index != 0) {
            recursiveStartCarry(null, head, index, 0);
        }
        return value;
    }
    private ListItem<T> findRecursive(ListItem<T> current, int targetIndex, int currentIndex) {
        if (currentIndex == targetIndex) {
            return current;
        }
        return findRecursive(current.next, targetIndex, currentIndex + 1);
    }
    private void recursiveStartCarry(ListItem<T> prev, ListItem<T> current, int targetIndex, int currentIndex) {
        if (currentIndex == targetIndex) {
            prev.next = current.next;

            if (current.next == null) {
                tail = prev;
            }
            current.next = head;
            head = current;
            return;
        }
        recursiveStartCarry(current, current.next, targetIndex, currentIndex + 1);
    }
}
