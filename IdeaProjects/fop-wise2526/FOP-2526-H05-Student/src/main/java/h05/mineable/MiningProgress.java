package h05.mineable;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Represents the progress of a mining operation.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public enum MiningProgress {

    /**
     * Indicates that the mining operation has not started yet.
     */
    @DoNotTouch
    UNSTARTED,

    /**
     * Indicates that the mining operation is currently in progress.
     */
    @DoNotTouch
    IN_PROGRESS,

    /**
     * Indicates that the mining operation has been completed.
     */
    @DoNotTouch
    COMPLETED;

    /**
     * Constructs a new {@link MiningProgress} instance.
     */
    @DoNotTouch
    MiningProgress() {
    }
}
