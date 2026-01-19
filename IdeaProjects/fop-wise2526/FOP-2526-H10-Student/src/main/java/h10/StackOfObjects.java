package h10;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.Stack;
import java.util.Vector;

/**
 * This class is a student implementation of a stack, which deviates from the standard {@link Stack} class
 * in the Java Collections Framework. It is designed to demonstrate custom stack behavior and does not
 * include all features or optimizations found in the standard implementation.
 * <p>
 * A Stack is an extension of {@link Vector} that provides specific methods, such as {@link #push}, to manage a variable number
 * of elements in a last-in-first-out (LIFO) manner.
 */
@SuppressWarnings({"ManualArrayCopy","unchecked"})
public class StackOfObjects<O> {

    /**
     * This array contains all objects of the stack.
     * The last index represents the top of the stack.
     */
    @StudentImplementationRequired("H10.1")
    private O[] objects = (O[]) new Object[0];

    /**
     * Extends the stack by one space.
     * Note that there are better solutions for this purpose,
     * such as using streams, which you will learn about in a future exercise.
     *
     * @return a new array that is the original array extended by one space
     */
    @StudentImplementationRequired("H10.1")
    private O[] expandStack() {
        O[] result = (O[]) new Object[objects.length + 1];
        for (int i = 0; i < result.length - 1; i++) {
            result[i] = objects[i];
        }
        return result;
    }

    /**
     * Shortens the stack by one space.
     * Note that there are better solutions for this purpose,
     * such as using streams, which you will learn about in a future exercise.
     *
     * @return a new array that is the original array shortened by the last item.
     */
    @StudentImplementationRequired("H10.1")
    private O[] shrinkStack() {
        O[] result = (O[]) new Object[objects.length - 1];
        for (int i = 0; i < result.length; i++) {
            result[i] = objects[i];
        }
        return result;
    }

    /**
     * Adds the given object to the top of this stack.
     *
     * @param object the object
     */
    @StudentImplementationRequired("H10.1")
    public void push(O object) {
        checkNotNull(object);
        objects = expandStack();
        objects[objects.length - 1] = object;
    }

    /**
     * Removes the object at the top of this stack and returns it.
     *
     * @return The object at the top of this stack.
     * @throws ArrayIndexOutOfBoundsException if this stack is empty.
     */
    @StudentImplementationRequired("H10.1")
    public O pop() {
        checkNotEmpty();
        O result = objects[objects.length - 1];
        objects = shrinkStack();
        return result;
    }

    /**
     * Returns the element at the specified position in this stack.
     *
     * <p>This method is modeled after {@link Vector#get(int)} from the stack's superclass.</p>
     *
     * @param index index of the element to return
     * @return object at the specified index
     * @throws ArrayIndexOutOfBoundsException if the index is out of range
     */
    @StudentImplementationRequired("H10.1")
    public O get(int index) {
        validateIndex(index);
        return objects[index];
    }

    /**
     * Replaces the object at the specified position with the specified object.
     *
     * <p>This method is modeled after {@link Vector#set(int, Object)} from the stack's superclass.</p>
     *
     * @param index  index of the object to replace
     * @param object to be stored at the specified position
     * @return the object previously at the specified position
     * @throws ArrayIndexOutOfBoundsException if the index is out of range
     * @throws IllegalArgumentException       if the object is null
     */
    @StudentImplementationRequired("H10.1")
    public O set(int index, O object) {
        checkNotNull(object);
        validateIndex(index);
        O oldObj = objects[index];
        objects[index] = object;
        return oldObj;
    }

    /**
     * Returns a stack containing an arbitrary number of objects.
     * The last object is the top object
     *
     * @param objects the elements to be contained in the list
     * @param <O>     the new stack's element type
     * @return a stack containing the specified objects
     */
    @SafeVarargs
    @StudentImplementationRequired("H10.1")
    public static <O> StackOfObjects<O> of(O... objects) {
        StackOfObjects<O> stack = new StackOfObjects<>();
        stack.objects = objects;
        return stack;
    }

    /**
     * Returns the number of components in this stack.
     *
     * <p>This method is modeled after {@link Vector#size()} from the stack's superclass.</p>
     *
     * @return the number of components in this stack
     */
    @DoNotTouch
    public int size() {
        return objects.length;
    }

    /**
     * Checks if the specified index is valid for the current stack.
     *
     * @param index the index to check
     * @throws IllegalArgumentException if the index is out of range
     */
    @DoNotTouch
    private void validateIndex(int index) {
        if (index < 0 || index >= objects.length) {
            throw new IllegalArgumentException("invalid index for stack of size %d: %d".formatted(size(), index));
        }
    }

    /**
     * Checks if the specified element is null.
     *
     * @param element the element to check
     * @throws IllegalArgumentException if the element is null
     */
    @DoNotTouch
    private void checkNotNull(Object element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot add null to the stack");
        }
    }

    /**
     * Checks if the stack is empty.
     *
     * @throws IllegalStateException if the stack is empty
     */
    @DoNotTouch
    private void checkNotEmpty() {
        if (size() <= 0) {
            throw new IllegalStateException("stack is empty");
        }
    }
}
