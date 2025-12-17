package h08.nodes;

import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;
import h08.functions.MyDoubleFunction;

/**
 * A node that applies a unary MyDoubleFunction to one operand.
 */
public class UnaryNode implements Node {

    private final MyDoubleFunction function;
    private final Node operand;

    /**
     * @param function the function to be applied to the one operand
     * @param operand  the single operand
    */
    public UnaryNode(MyDoubleFunction function, Node operand) {
        this.function = function;
        this.operand = operand;
    }

    @StudentImplementationRequired("H8.5.1")
    @Override
    public double evaluate() {
        double operandResult = operand.evaluate();
        double operationResult = function.apply(operandResult);
        return operationResult;
    }
}
