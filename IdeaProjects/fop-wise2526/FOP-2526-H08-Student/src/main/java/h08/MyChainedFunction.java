package h08;

import h08.nodes.Node;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;
import h08.functions.MyDoubleFunction;
import h08.nodes.ChainNode;

/**
 * Example usage of ChainNode
 */
public final class MyChainedFunction {

    @DoNotTouch
    private MyChainedFunction() {
        // do not instantiate helper class
    }

    /**
     * Create a ChainNode that computes sqrt(q + 1 + 1).
     *
     * @param q the initial operand q
     * @return a ChainNode that computes sqrt(q + 1 + 1)
     */
    @StudentImplementationRequired("H8.7.2")
    public static Node add2sqrt(Node q) {
        MyDoubleFunction firstAddition = input -> input + 1.0;
        MyDoubleFunction secondAddition = result -> result + 1.0;
        MyDoubleFunction rootOfTheNumber = Math::sqrt;
        MyDoubleFunction[] operations = new MyDoubleFunction[]{firstAddition, secondAddition, rootOfTheNumber};
        ChainNode resultNode = new ChainNode(q, operations);
        return resultNode;
    }
}
