package h08.nodes;

import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;
import h08.functions.MyDoubleFunction;

/**
 * A node that summerizes a chain of unary function applications to one node
 * using composition.
 */
public class ChainNode implements Node {

    private final Node node;
    private final MyDoubleFunction[] functions;

    /**
     * @param node      the single operand to start with
     * @param functions all functions to be applied in a chain (starting with the first one)
     */
    public ChainNode(Node node, MyDoubleFunction... functions) {
        this.node = node;
        this.functions = functions;
    }

    @Override
    @StudentImplementationRequired("H8.7.1")
    public double evaluate() {
        double startValue = node.evaluate();
        double currentResult = startValue;
        int functionIndex = 0;
        while (functionIndex < functions.length) {
            double newValue = functions[functionIndex].apply(currentResult);
            currentResult = newValue;
            functionIndex = functionIndex + 1;
        }
        return currentResult;
    }
}
