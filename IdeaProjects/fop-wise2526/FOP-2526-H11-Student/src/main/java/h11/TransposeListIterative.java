package h11;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * An iterative implementation of a self-organizing list that transposes accessed elements with their previous element.
 *
 * @param <T> the type of elements in the list
 * @author Nhan Huynh
 */
@DoNotTouch
public class TransposeListIterative<T> extends TransposeList<T> implements SelfOrganizingList<T> {

    /**
     * Creates a new empty list.
     */
    @DoNotTouch
    public TransposeListIterative() {
    }

    /**
     * Creates a list with the given elements.
     *
     * @param elements the elements to be added to the list
     */
    @DoNotTouch
    public TransposeListIterative(@NotNull T[] elements) {
        super(elements);
    }

    @StudentImplementationRequired("H11.2")
    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        ListItem<T> currentElement = head;
        ListItem<T> onePrev = null;
        ListItem<T> twoPrev = null;
        int myIndex = 0;
        while (myIndex < index) {
            twoPrev = onePrev;
            onePrev = currentElement;
            currentElement = currentElement.next;
            myIndex++;
        }
        T answer = currentElement.key;
        if (index > 0) {
            onePrev.next = currentElement.next;
            currentElement.next = onePrev;

            if (twoPrev == null) {
                head = currentElement;
            } else {
                twoPrev.next = currentElement;
            }
            if (onePrev.next == null) {
                tail = onePrev;
            }
        }
        return answer;
    }
}
