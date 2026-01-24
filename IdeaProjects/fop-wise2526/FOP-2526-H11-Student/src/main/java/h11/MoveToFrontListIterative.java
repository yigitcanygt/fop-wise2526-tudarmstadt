package h11;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * An iterative implementation of a self-organizing list that moves accessed elements to the front of the list.
 *
 * @param <T> the type of elements in the list
 * @author Nhan Huynh
 */
@DoNotTouch
public class MoveToFrontListIterative<T> extends MoveToFrontList<T> implements SelfOrganizingList<T> {

    /**
     * Creates a new empty list.
     */
    @DoNotTouch
    public MoveToFrontListIterative() {
    }

    /**
     * Creates a list with the given elements.
     *
     * @param elements the elements to be added to the list
     */
    @DoNotTouch
    public MoveToFrontListIterative(@NotNull T[] elements) {
        super(elements);
    }

    @StudentImplementationRequired("H11.1")
    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        ListItem<T> current = head;
        ListItem<T> prevElement = null;
        int counter = 0;
        while (counter < index) {
            prevElement = current;
            current = current.next;
            counter++;
        }
        T result = current.key;
        if (index != 0) {
            prevElement.next = current.next;

            if (current.next == null) {
                tail = prevElement;
            }
            current.next = head;
            head = current;
        }
        return result;
    }
}
