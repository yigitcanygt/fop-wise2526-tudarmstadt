package h08.nodes;

/**
 * Represents an arithmetic sub-expression which can be evaluated to its result.
 * Implementors will provide evaluation logic depending on their operand count.
 */
public interface Node {

    /**
     * Evaluates this sub-expression, i.e. computes the real number that is the result of this expression.
     *
     * @return the result of this expression
     */
    double evaluate();

}
