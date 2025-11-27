package h05.base.mineable;

import fopbot.FieldEntity;
import fopbot.World;
import h05.base.entity.Loot;
import h05.mineable.Mineable;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * A basic implementation of an inventory that can hold mineable items.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public final class BasicInventory implements Inventory {

    /**
     * The default capacity of the inventory.
     */
    @DoNotTouch
    public static final int DEFAULT_CAPACITY = 5;

    /**
     * The default size of the inventory.
     */
    @DoNotTouch
    public static final int DEFAULT_SIZE = 5;

    /**
     * The maximum number of items the inventory can hold.
     */
    @DoNotTouch
    private final int capacity;

    /**
     * The names of the items in the inventory.
     */
    private String[] names;

    /**
     * The amounts of each item in the inventory.
     */
    private int[] amounts;

    /**
     * The current size of the inventory, i.e., the number of different items it can hold.
     */
    @DoNotTouch
    private int size;

    /**
     * The number of items currently in the inventory.
     */
    @DoNotTouch
    private int numberOfItems;

    /**
     * The index for the next item to be added to the inventory.
     */
    @DoNotTouch
    private int nextIndex;

    /**
     * Constructs a new {@link BasicInventory} instance with the specified capacity and size.
     *
     * @param capacity the maximum number of items the inventory can hold
     * @param size     the initial size of the inventory, i.e., the number of different items it can hold
     */
    @DoNotTouch
    public BasicInventory(int capacity, int size) {
        this.capacity = capacity;
        this.names = new String[size];
        this.amounts = new int[size];
    }

    /**
     * Constructs a new {@link BasicInventory} instance with the default capacity of {@value DEFAULT_CAPACITY} and size
     * of {@value DEFAULT_SIZE}.
     */
    @DoNotTouch
    public BasicInventory() {
        this(DEFAULT_CAPACITY, DEFAULT_SIZE);
    }

    @DoNotTouch
    @Override
    public int capacity() {
        return capacity;
    }

    @DoNotTouch
    @Override
    public int size() {
        return size;
    }

    @DoNotTouch
    @Override
    public int numberOfItems() {
        return numberOfItems;
    }

    @DoNotTouch
    @Override
    public boolean add(@NotNull Mineable item) {
        if (numberOfItems == capacity) {
            return false;
        }
        final String itemName = item.getName();

        // Check if item already exists in inventory
        for (int i = 0; i < size; i++) {
            if (names[i].equals(itemName)) {
                amounts[i]++;
                numberOfItems++;
                updateGui(item);
                return true;
            }
        }

        // Increase storage if necessary
        if (size == names.length) {
            final String[] newNames = new String[names.length * 2];
            final int[] newAmounts = new int[amounts.length * 2];
            System.arraycopy(names, 0, newNames, 0, names.length);
            System.arraycopy(amounts, 0, newAmounts, 0, amounts.length);
            names = newNames;
            amounts = newAmounts;
        }

        // Add new item to inventory
        names[nextIndex] = itemName;
        amounts[nextIndex] = 1;
        numberOfItems++;
        nextIndex++;
        size++;
        updateGui(item);
        return true;
    }

    @DoNotTouch
    private void updateGui(@NotNull Mineable item) {
        for (FieldEntity entity : World.getGlobalWorld().getAllFieldEntities()) {
            if (entity instanceof Loot loot && loot.getMineable().equals(item)) {
                World.getGlobalWorld().removeFieldEntity(entity);
            }
        }
    }

    @DoNotTouch
    @Override
    public String[] getNames() {
        return names;
    }

    @DoNotTouch
    @Override
    public int getAmount(@NotNull String itemName) {
        for (int i = 0; i < size; i++) {
            if (names[i].equals(itemName)) {
                return amounts[i];
            }
        }
        return 0;
    }
}
