package h11;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * This class provides a skeletal implementation of the {@link SelfOrganizingList} interface to minimize the effort
 * required to implement this interface by implementing common operations.
 *
 * @param <T> the type of elements in this list
 * @author Nhan Huynh
 */
@DoNotTouch
public abstract class AbstractSelfOrganizingList<T> implements SelfOrganizingList<T> {

    /**
     * The reference to the head (first element) of the list.
     */
    @DoNotTouch
    protected @Nullable ListItem<T> head;

    /**
     * The reference to the tail (last element) of the list.
     */
    @DoNotTouch
    protected @Nullable ListItem<T> tail;

    /**
     * The number of elements in the list.
     */
    @DoNotTouch
    protected int size;

    /**
     * Creates an empty list.
     */
    @DoNotTouch
    public AbstractSelfOrganizingList() {
    }

    /**
     * Creates a list with the given elements.
     *
     * @param elements the elements to be added to the list
     */
    public AbstractSelfOrganizingList(@NotNull T[] elements) {
        if (elements.length == 0) {
            return;
        }
        head = new ListItem<>(elements[0]);
        tail = head;
        for (int i = 1; i < elements.length; i++) {
            tail.next = new ListItem<>(elements[i]);
            tail = tail.next;
        }
        size = elements.length;
    }

    @DoNotTouch
    @Override
    public int size() {
        return size;
    }

    @DoNotTouch
    @Override
    public void addFirst(T key) {
        ListItem<T> newHead = new ListItem<>(key);
        newHead.next = head;
        if (tail == null) {
            tail = newHead;
        }
        head = newHead;
        size++;
    }

    @DoNotTouch
    @Override
    public void addLast(T key) {
        ListItem<T> newItem = new ListItem<>(key);
        if (tail == null) {
            head = newItem;
        } else {
            tail.next = newItem;
        }
        tail = newItem;
        size++;
    }

    @DoNotTouch
    @Override
    public void remove(T key) throws NoSuchElementException {
        if (head == null) {
            throw new NoSuchElementException();
        }
        if (head.key.equals(key)) {
            head = head.next;
            size--;
            return;
        }
        for (ListItem<T> cursor = head; cursor.next != null; cursor = cursor.next) {
            if (cursor.next.key.equals(key)) {
                if (cursor.next == tail) {
                    tail = cursor;
                }
                cursor.next = cursor.next.next;
                size--;
                return;
            }
        }
        throw new NoSuchElementException();
    }

    @DoNotTouch
    @Override
    public @NotNull BidirectionalIterator<T> iterator() {
        return new BidirectionalListIterator<>(this);
    }

    @DoNotTouch
    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof AbstractSelfOrganizingList<?> that
                && size == that.size
                && Objects.equals(head, that.head)
                && Objects.equals(tail, that.tail);
    }

    @DoNotTouch
    @Override
    public int hashCode() {
        return Objects.hash(head, tail, size);
    }

    @DoNotTouch
    @Override
    public String toString() {
        if (head == null) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder(size);
        builder.append("[");
        builder.append(head.key);
        for (ListItem<T> cursor = head.next; cursor != null; cursor = cursor.next) {
            builder.append(", ").append(cursor.key);
        }
        builder.append("]");
        return builder.toString();
    }
}
