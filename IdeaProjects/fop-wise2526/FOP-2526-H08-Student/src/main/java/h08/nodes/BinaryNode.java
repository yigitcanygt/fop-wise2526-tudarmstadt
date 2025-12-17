package h08.nodes;

import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;
import h08.functions.MyDoubleBiFunction;

/**
 * A node that applies a DoubleBiFunction to two operands.
 */
public class BinaryNode implements Node {

    private final MyDoubleBiFunction function;
    private final Node lhs;
    private final Node rhs;

    /**
     * @param function the binary function to be applied to the two operands
     * @param lhs      the left-hand-side/first operand
     * @param rhs      the right-hand-side/second operand
     */
    public BinaryNode(MyDoubleBiFunction function, Node lhs, Node rhs) {
        this.function = function;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @StudentImplementationRequired("H8.5.2")
    @Override
    public double evaluate() {
        double leftOperand = lhs.evaluate();
        double rightOperand = rhs.evaluate();
        double calculatedResult = function.apply(leftOperand, rightOperand);
        return calculatedResult;
    }
}
