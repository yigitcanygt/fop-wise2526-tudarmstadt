package h11;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * A recursive implementation of a self-organizing list that transposes accessed elements with their previous element.
 *
 * @param <T> the type of elements in the list
 * @author Nhan Huynh
 */
@DoNotTouch
public class TransposeListRecursive<T> extends TransposeList<T> implements SelfOrganizingList<T> {

    /**
     * Creates a new empty list.
     */
    @DoNotTouch
    public TransposeListRecursive() {
    }

    /**
     * Creates a list with the given elements.
     *
     * @param elements the elements to be added to the list
     */
    @DoNotTouch
    public TransposeListRecursive(@NotNull T[] elements) {
        super(elements);
    }

    @StudentImplementationRequired("H11.2")
    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        ListItem<T> found = searchRecursive(head, index, 0);
        T myValue = found.key;
        if (index > 0) {
            recursiveChange(null, null, head, index, 0);
        }
        return myValue;
    }
    private ListItem<T> searchRecursive(ListItem<T> element, int target, int location) {
        if (location == target) {
            return element;
        }
        return searchRecursive(element.next, target, location + 1);
    }
    private void recursiveChange(ListItem<T> firstPrev, ListItem<T> prev, ListItem<T> now, int target, int location) {
        if (location == target) {
            prev.next = now.next;
            now.next = prev;
            if (firstPrev == null) {
                head = now;
            } else {
                firstPrev.next = now;
            }
            if (prev.next == null) {
                tail = prev;
            }
            return;
        }
        recursiveChange(prev, now, now.next, target, location + 1);
    }
}
