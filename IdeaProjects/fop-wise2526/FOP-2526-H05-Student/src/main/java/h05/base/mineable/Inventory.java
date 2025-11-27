package h05.base.mineable;

import h05.mineable.Mineable;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * An inventory for storing mineable items with a defined capacity.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public interface Inventory {

    /**
     * Returns the maximum number of items the inventory can hold.
     *
     * @return the maximum number of items the inventory can hold
     */
    @DoNotTouch
    int capacity();

    /**
     * Returns the current size of the inventory.
     *
     * @return the number of items currently in the inventory
     */
    @DoNotTouch
    int size();

    /**
     * Returns the number of items in the inventory.
     *
     * @return the number of items in the inventory
     */
    @DoNotTouch
    int numberOfItems();

    /**
     * Adds a mineable item to the inventory if there is enough space.
     *
     * @param item the mineable item to add
     * @return {@code true} if the item was added, {@code false} otherwise
     */
    @DoNotTouch
    boolean add(@NotNull Mineable item);

    /**
     * Returns the names of all items in the inventory.
     *
     * @return an array of item names in the inventory
     */
    @DoNotTouch
    @NotNull
    String[] getNames();

    /**
     * Returns the amount of a specific item in the inventory.
     *
     * @param itemName the name of the item to check
     * @return the amount of the specified item in the inventory
     */
    @DoNotTouch
    int getAmount(@NotNull String itemName);
}
