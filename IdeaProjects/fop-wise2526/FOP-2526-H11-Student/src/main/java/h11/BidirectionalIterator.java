package h11;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A bidirectional iterator is an iterator that allows traversing a collection in both directions.
 *
 * @param <T> the type of elements returned by this iterator
 * @author Nhan Huynh
 */
@DoNotTouch
public interface BidirectionalIterator<T> extends Iterator<T> {

    /**
     * Returns {@code true} if this iterator has more elements when traversing the collection in the reverse direction.
     *
     * @return {@code true} if this iterator has more elements when traversing the collection in the reverse direction
     */
    @DoNotTouch
    boolean hasPrevious();

    /**
     * Returns the previous element in the collection and moves the cursor position backwards.
     *
     * @return the previous element in the collection
     * @throws NoSuchElementException if the iteration has no previous element
     */
    @DoNotTouch
    T previous() throws NoSuchElementException;

    /**
     * Returns {@code true} if this iterator has more elements when traversing the collection in the forward direction.
     *
     * @return {@code true} if this iterator has more elements when traversing the collection in the forward direction
     */
    @DoNotTouch
    @Override
    boolean hasNext();

    /**
     * Returns the next element in the collection and moves the cursor position forward.
     *
     * @return the next element in the collection
     * @throws NoSuchElementException if the iteration has no next element
     */
    @DoNotTouch
    @Override
    T next() throws NoSuchElementException;

    /**
     * Adds the specified element before the current cursor position.
     *
     * @param element the element to be added
     */
    @DoNotTouch
    void add(T element);

    /**
     * Removes the last element returned by this iterator from the collection.
     *
     * @throws IllegalStateException if there is no last element returned by this iterator
     */
    @DoNotTouch
    @Override
    void remove() throws IllegalStateException;
}
