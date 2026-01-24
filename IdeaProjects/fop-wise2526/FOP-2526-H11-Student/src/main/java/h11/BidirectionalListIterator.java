package h11;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.NoSuchElementException;

/**
 * An implementation of {@link BidirectionalIterator} that allows iterating over the list in both directions for
 * a {@link SelfOrganizingList}.
 */
class BidirectionalListIterator<T> implements BidirectionalIterator<T> {

    /**
     * The list to iterate over.
     */
    private final @NotNull AbstractSelfOrganizingList<T> list;

    /**
     * The last element returned by a call to {@code next()} or {@code previous()}.
     * This field is used to determine whether a subsequent call to {@code remove()}
     * is valid. A call to {@code remove()} is only allowed if it directly follows
     * a call to {@code next()} or {@code previous()}, and it cannot be called twice
     * in a row without an intervening cursor movement.
     * <p>
     * After a call to {@code add()}, this field is set to {@code null} to prevent
     * an immediate {@code remove()}. After a call to {@code remove()}, it is also
     * set to {@code null} because the previously returned element no longer exists
     * in the list.
     */
    @Nullable ListItem<T> lastReturned;

    /**
     * The cursor for the current position in the list.
     */
    @Nullable ListItem<T> cursor;

    /**
     * The previous references of the cursor for the reverse iteration.
     */
    @Nullable ListItem<ListItem<T>> previouses;

    /**
     * Creates a new iterator for the given list.
     *
     * @param list the list to iterate over
     */
    BidirectionalListIterator(@NotNull AbstractSelfOrganizingList<T> list) {
        this.list = list;
        this.cursor = list.head;
    }

    @StudentImplementationRequired("H11.4.1")
    @Override
    public boolean hasPrevious() {
        // TODO H11.4.1
        return previouses != null;
    }

    @StudentImplementationRequired("H11.4.1")
    @Override
    public T previous() throws NoSuchElementException {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        ListItem<T> returning = previouses.key;
        lastReturned = returning;
        cursor = returning;
        previouses = previouses.next;
        return returning.key;
    }

    @StudentImplementationRequired("H11.4.1")
    @Override
    public boolean hasNext() {
        return cursor != null;
    }

    @StudentImplementationRequired("H11.4.1")
    @Override
    public T next() throws NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        ListItem<T> result = cursor;
        lastReturned = result;
        ListItem<ListItem<T>> newPrev = new ListItem<>(result);
        newPrev.next = previouses;
        previouses = newPrev;
        cursor = cursor.next;
        return result.key;
    }

    @StudentImplementationRequired("H11.4.1")
    @Override
    public void add(T element) {
        ListItem<T> neu = new ListItem<>(element);
        if (list.head == null) {
            list.head = neu;
            list.tail = neu;
            cursor = null;
        } else {
            if (previouses == null) {
                neu.next = list.head;
                list.head = neu;
            } else {
                ListItem<T> prev = previouses.key;
                neu.next = prev.next;
                prev.next = neu;

                if (neu.next == null) {
                    list.tail = neu;
                }
            }
        }
        list.size++;
        lastReturned = null;
    }

    @StudentImplementationRequired("H11.4.1")
    @Override
    public void remove() throws IllegalStateException {
        if (lastReturned == null) {
            throw new IllegalStateException();
        }
        ListItem<T> deleted = lastReturned;
        if (cursor == deleted) {
            if (previouses == null) {
                list.head = deleted.next;
                cursor = list.head;
            } else {
                ListItem<T> prev = previouses.key;
                prev.next = deleted.next;
                cursor = deleted.next;
                if (prev.next == null) {
                    list.tail = prev;
                }
            }
        } else {
            ListItem<T> findPrev = null;
            ListItem<T> search = list.head;
            while (search != null && search != deleted) {
                findPrev = search;
                search = search.next;
            }
            if (findPrev == null) {
                list.head = deleted.next;
            } else {
                findPrev.next = deleted.next;
                if (deleted.next == null) {
                    list.tail = findPrev;
                }
            }
            if (previouses != null && previouses.key == deleted) {
                previouses = previouses.next;
            }
        }
        list.size--;
        lastReturned = null;
    }
}


