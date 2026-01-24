package h11;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * A self-organizing list that transposes accessed elements with the previous element in the list moving it one
 * position to the front.
 *
 * @param <T> the type of elements in the list
 * @author Nhan Huynh
 */
@DoNotTouch
public abstract class TransposeList<T> extends AbstractSelfOrganizingList<T> implements SelfOrganizingList<T> {

    /**
     * Creates a new empty list.
     */
    @DoNotTouch
    public TransposeList() {
    }

    /**
     * Creates a list with the given elements.
     *
     * @param elements the elements to be added to the list
     */
    @DoNotTouch
    public TransposeList(@NotNull T[] elements) {
        super(elements);
    }

    @DoNotTouch
    @Override
    public @NotNull Strategy strategy() {
        return Strategy.TRANSPOSE;
    }
}
