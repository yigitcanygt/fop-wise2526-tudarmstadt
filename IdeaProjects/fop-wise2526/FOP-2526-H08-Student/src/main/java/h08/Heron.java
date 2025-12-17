package h08;

import h08.nodes.Node;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;
import h08.nodes.ArrayNode;
import h08.nodes.BinaryNode;
import h08.nodes.UnaryNode;

/**
 * Implementation of Heron's formula
 */
public final class Heron {

    @DoNotTouch
    private Heron() {
        // do not instantiate helper class
    }

    /**
     * Creates a node that computes the Euclidean distance between point A and B.
     *
     * @param ax A's x coordinate
     * @param ay A's y coordinate
     * @param bx B's x coordinate
     * @param by B's y coordinate
     * @return a node that computes dist(A, B)
     */
    @StudentImplementationRequired("H8.6.1")
    public static Node pointDistance(Node ax, Node ay, Node bx, Node by) {
        Node differenceXNode = new BinaryNode((left, right) -> left - right, ax, bx);
        Node squareXNode = new UnaryNode(value -> value * value, differenceXNode);
        Node differenceYNode = new BinaryNode((first, second) -> first - second, ay, by);
        Node squareYNode = new UnaryNode(number -> number * number, differenceYNode);
        Node totalNode = new BinaryNode(Double::sum, squareXNode, squareYNode);
        Node rootNode = new UnaryNode(Math::sqrt, totalNode);
        return rootNode;
    }

    /**
     * Creates a node that computes half of a triangle's perimeter
     * given the lengths of all three edges.
     *
     * @param a length of first edge
     * @param b length of second edge
     * @param c length of third edge
     * @return a node that computes half of the triangle's perimeter.
     */
    @StudentImplementationRequired("H8.6.2")
    public static Node halfTrianglePerimeter(Node a, Node b, Node c) {
        Node[] edges = new Node[]{a, b, c};
        Node totalNode = new ArrayNode(values -> {
            double total = 0.0;
            int index = 0;
            while (index < values.length) {
                total = total + values[index];
                index++;
            }
            return total;
        }, edges);
        Node halfNode = new UnaryNode(value -> value / 2.0, totalNode);
        return halfNode;
    }

    /**
     * Creates a node that computes the area of the triangle ABC using Heron's formula.
     *
     * @param ax A's x coordinate
     * @param ay A's y coordinate
     * @param bx B's x coordinate
     * @param by B's y coordinate
     * @param cx C's x coordinate
     * @param cy C's y coordinate
     * @return a node that computes the triangle's area
     */
    @StudentImplementationRequired("H8.6.3")
    public static Node heron(Node ax, Node ay, Node bx, Node by, Node cx, Node cy) {
        Node edgeA = pointDistance(bx, by, cx, cy);
        Node edgeB = pointDistance(ax, ay, cx, cy);
        Node edgeC = pointDistance(ax, ay, bx, by);
        Node sNode = halfTrianglePerimeter(edgeA, edgeB, edgeC);
        Node sDifferenceA = new BinaryNode((firstValue, secondValue) -> firstValue - secondValue, sNode, edgeA);
        Node sDifferenceB = new BinaryNode((leftValue, rightValue) -> leftValue - rightValue, sNode, edgeB);
        Node sDifferenceC = new BinaryNode((firstNumber, secondNumber) -> firstNumber - secondNumber, sNode, edgeC);
        Node[] multipliers = new Node[]{sNode, sDifferenceA, sDifferenceB, sDifferenceC};
        Node multiplicationNode = new ArrayNode(numbers -> {
            double result = 1.0;
            int position = 0;
            while (position < numbers.length) {
                result = result * numbers[position];
                position = position + 1;
            }
            return result;
        }, multipliers);
        Node resultNode = new UnaryNode(Math::sqrt, multiplicationNode);
        return resultNode;
    }
}
