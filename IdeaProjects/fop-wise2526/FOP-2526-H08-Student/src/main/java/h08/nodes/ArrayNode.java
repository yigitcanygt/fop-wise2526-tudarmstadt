package h08.nodes;

import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;
import h08.functions.MyDoubleArrayFunction;

/**
 * A node that applies a DoubleArrayFunction to an arbitrary amount of operands.
 */
public class ArrayNode implements Node {

    private final MyDoubleArrayFunction function;
    private final Node[] operands;

    /**
     * @param function the function to be applied to the operands
     * @param operands the operands to apply the function to
     */
    public ArrayNode(MyDoubleArrayFunction function, Node... operands) {
        this.function = function;
        this.operands = operands;
    }

    @StudentImplementationRequired("H8.5.3")
    @Override
    public double evaluate() {
        double[] results = new double[operands.length];
        int index = 0;
        while (index < operands.length) {
            double singleResult = operands[index].evaluate();
            results[index] = singleResult;
            index = index + 1;
        }
        double ultimateMemory = function.apply(results);
        return ultimateMemory;
    }
}
