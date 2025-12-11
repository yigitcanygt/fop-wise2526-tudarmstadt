package h07;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * represents a peano number
 */
public class PeanoNumber {

    @DoNotTouch
    private final PeanoValue type;
    @DoNotTouch
    private final PeanoNumber predecessor;

    /**
     * constructs a zero
     */
    @DoNotTouch
    public PeanoNumber() {
        type = PeanoValue.Zero;
        predecessor = null;
    }

    /**
     * constructs a successor
     * @param predecessor the predecessor of the successor
     */
    @DoNotTouch
    public PeanoNumber(PeanoNumber predecessor) {
        if (predecessor == null) {
            throw new IllegalArgumentException("Predecessor cannot be null!");
        }
        type = PeanoValue.Successor;
        this.predecessor = predecessor;
    }

    /**
     * returns the type of a peano number
     * @return the type of a peano number
     */
    @DoNotTouch
    public PeanoValue getType() {
        return type;
    }

    /**
     * returns the predecessor of a peano number
     * does not work on a zero
     * @return the predecessor of a peano number
     */
    @DoNotTouch
    public PeanoNumber getPredecessor() {
        if (type == PeanoValue.Zero) {
            throw new UnsupportedOperationException("Zero does not have a Predecessor!");
        }
        return predecessor;
    }

    /**
     * returns a string representation of a peano number
     * @return peano number as a string
     */
    @DoNotTouch
    public String toString() {
        if (type == PeanoValue.Zero) {
            return "(" + type + ")";
        }
        return "(" + type + predecessor + ")";
    }

    /**
     * recursively converts a peano number into an int
     * @return peano number as an int
     */
    @StudentImplementationRequired("H7.1.1")
    public int asIntRecursive() {
        if (getType() == PeanoValue.Zero) {
            return 0;
        }
        int previousValue = getPredecessor().asIntRecursive();
        return previousValue + 1;
    }

    /**
     * imperatively converts a peano number into an int
     * @return peano number as an int
     */
    @StudentImplementationRequired("H7.1.2")
    public int asIntIterative() {
        int counter = 0;
        PeanoNumber now = this;
        while (now.getType() != PeanoValue.Zero) {
            counter++;
            now = now.getPredecessor();
        }
        return counter;
    }

    /**
     * recursively converts an int into a peano number
     * @return int as peano number
     */
    @DoNotTouch
    public static PeanoNumber fromInt(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("number may not be below zero!");
        }
        if (number == 0) {
            return new PeanoNumber();
        }
        return new PeanoNumber(fromInt(number-1));
    }

    /**
     * iteratively converts an int into a peano number
     * @return int as peano number
     */
    @DoNotTouch
    public static PeanoNumber fromInt2(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("number may not be below zero!");
        }
        PeanoNumber result = new PeanoNumber();
        while (number != 0) {
            result = new PeanoNumber(result);
            number -= 1;
        }
        return result;
    }

    /**
     * recursively adds a peano number onto this one
     * @param peanoNumber the peano number to add
     * @return this + peanoNumber
     */
    @StudentImplementationRequired("H7.1.3")
    public PeanoNumber addRecursive(PeanoNumber peanoNumber) {
        if (peanoNumber.getType() == PeanoValue.Zero) {
            return this;
        }
        PeanoNumber newPrevious = peanoNumber.getPredecessor();
        PeanoNumber mediateResult = this.addRecursive(newPrevious);
        return new PeanoNumber(mediateResult);
    }

    /**
     * iteratively adds a peano number onto this one
     * @param peanoNumber the number to add
     * @return this + peanoNumber
     */
    @StudentImplementationRequired("H7.1.4")
    public PeanoNumber addIterative(PeanoNumber peanoNumber) {
        PeanoNumber answer = this;
        PeanoNumber numberToBeAdded = peanoNumber;
        while (numberToBeAdded.getType() != PeanoValue.Zero) {
            answer = new PeanoNumber(answer);
            numberToBeAdded = numberToBeAdded.getPredecessor();
        }
        return answer;
    }

    /**
     * recursively multiplies this peano number with another one
     * @param peanoNumber the number to multiply with
     * @return this + peanoNumber
     */
    @StudentImplementationRequired("H7.1.5")
    public PeanoNumber multiplyRecursive(PeanoNumber peanoNumber) {
        return (peanoNumber.getType() == PeanoValue.Zero) ? (new PeanoNumber()) : (this.addRecursive(this.multiplyRecursive(peanoNumber.getPredecessor())));
    }

    /**
     * imperatively multiplies this peano number with another one
     * @param peanoNumber the number to multiply with
     * @return this + peanoNumber
     */
    @StudentImplementationRequired("H7.1.6")
    public PeanoNumber multiplyIterative(PeanoNumber peanoNumber) {
        PeanoNumber multiplicationResult = new PeanoNumber();
        PeanoNumber remainingMultiplier = peanoNumber;
        while (remainingMultiplier.getType() != PeanoValue.Zero) {
            multiplicationResult = multiplicationResult.addIterative(this);
            remainingMultiplier = remainingMultiplier.getPredecessor();
        }
        return multiplicationResult;
    }
}
