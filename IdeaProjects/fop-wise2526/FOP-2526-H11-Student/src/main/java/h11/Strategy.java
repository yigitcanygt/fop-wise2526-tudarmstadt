package h11;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Defines the strategies for organizing the list.
 */
@DoNotTouch
public enum Strategy {
    /**
     * Moves the accessed element to the front of the list.
     */
    MOVE_TO_FRONT,
    /**
     * Moves the accessed element to one position closer to the front of the list.
     */
    TRANSPOSE,

    /**
     * Moves the accessed element to a random position between the front and the accessed element.
     */
    RANDOM
}
