package h11;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.util.Objects;

/**
 * A list item wraps a single element in a linked list data structure to allow linking of multiple items together.
 *
 * @param <T> the type of the key
 * @author Nhan Huynh
 */
@DoNotTouch
public final class ListItem<T> {

    /**
     * The key of the list item.
     */
    @DoNotTouch
    final T key;

    /**
     * The reference to the next element in the list.
     */
    @DoNotTouch
    @Nullable
    ListItem<T> next;

    /**
     * Creates a list item with the given key.
     *
     * @param key the key of the list item
     */
    @DoNotTouch
    public ListItem(T key) {
        this.key = key;
    }

    /**
     * Creates a list item sequence with the given keys.
     *
     * @param keys the keys of the list items
     */
    @DoNotTouch
    public ListItem(@NotNull T[] keys) {
        if (keys.length == 0) {
            throw new IllegalArgumentException("The item must be non-empty");
        }
        this.key = keys[0];
        ListItem<T> cursor = this;
        for (int i = 1; i < keys.length; i++) {
            cursor.next = new ListItem<>(keys[i]);
            cursor = cursor.next;
        }
    }

    @DoNotTouch
    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof ListItem<?> listItem
            && Objects.equals(key, listItem.key)
            && Objects.equals(next, listItem.next);
    }

    @DoNotTouch
    @Override
    public int hashCode() {
        return Objects.hash(key, next);
    }

    @DoNotTouch
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[").append(key).append("|");
        ListItem<T> cursor = next;
        while (cursor != null) {
            builder.append(cursor.key);
            builder.append(" -> ");
            cursor = cursor.next;
        }
        builder.append("null]");
        return builder.toString();
    }
}
