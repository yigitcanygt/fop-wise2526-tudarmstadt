package h11;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * An iterator-based implementation of a self-organizing list that transposes accessed elements with their previous element.
 *
 * @param <T> the type of elements in the list
 * @author Nhan Huynh
 */
@DoNotTouch
public class TransposeListIterator<T> extends TransposeList<T> implements SelfOrganizingList<T> {

    /**
     * Creates a new empty list.
     */
    @DoNotTouch
    public TransposeListIterator() {
    }

    /**
     * Creates a list with the given elements.
     *
     * @param elements the elements to be added to the list
     */
    @DoNotTouch
    public TransposeListIterator(@NotNull T[] elements) {
        super(elements);
    }

    @StudentImplementationRequired("H11.4.3")
    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        BidirectionalIterator<T> iteratorNesne = iterator();
        T resultValue = null;
        int counterValue = 0;
        while (iteratorNesne.hasNext() && counterValue <= index) {
            resultValue = iteratorNesne.next();
            counterValue++;
        }
        if (index == 0) {
            return resultValue;
        }
        iteratorNesne.remove();
        iteratorNesne.previous();
        iteratorNesne.add(resultValue);
        return resultValue;
    }
}
