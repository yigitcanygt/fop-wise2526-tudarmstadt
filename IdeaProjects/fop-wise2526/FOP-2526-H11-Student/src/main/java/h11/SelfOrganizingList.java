package h11;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.util.NoSuchElementException;

/**
 * A self-organizing list is a data structure that organizes its elements based on access patterns to improve
 * access times for frequently accessed elements.
 *
 * @param <T> the type of elements in the list
 * @author Nhan Huynh
 */
@DoNotTouch
public interface SelfOrganizingList<T> {

    /**
     * Returns the strategy used to organize the list.
     *
     * @return the strategy used to organize the list
     */
    @DoNotTouch
    @NotNull Strategy strategy();

    /**
     * Returns the number of elements in the list.
     *
     * @return the number of elements in the list
     */
    @DoNotTouch
    int size();

    /**
     * Adds the given key to the front of the list.
     *
     * @param key the key to add
     */
    @DoNotTouch
    void addFirst(T key);

    /**
     * Adds the given key to the end of the list.
     *
     * @param key the key to add
     */
    @DoNotTouch
    void addLast(T key);

    /**
     * Removes the given key from the list.
     *
     * @param key the key to remove
     * @throws NoSuchElementException if the key is not found in the list
     */
    @DoNotTouch
    void remove(T key) throws NoSuchElementException;

    /**
     * Returns the key at the given index.
     *
     * @param index the index of the key to return
     * @return the key at the given index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    @DoNotTouch
    T get(int index) throws IndexOutOfBoundsException;

    /**
     * Returns an iterator over the elements in the list.
     *
     * @return an iterator over the elements in the list
     */
    @DoNotTouch
    @NotNull BidirectionalIterator<T> iterator();
}
