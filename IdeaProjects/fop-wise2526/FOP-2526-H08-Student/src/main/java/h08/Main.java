package h08;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;
import org.tudalgo.algoutils.student.test.StudentTestUtils;
import h08.functions.MyDoubleFunction;
import h08.implementations.*;
import h08.nodes.Node;

/**
 * Main entry point in executing the program.
 */
public class Main {

    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) {
        sanityChecks();
        StudentTestUtils.printTestResults();
    }

    /**
     * Test student's implementation
     */
    @StudentImplementationRequired("H8.8")
    public static void sanityChecks() {
        MyDoubleFunction metodSqr = Methods::sqr;
        MyDoubleFunction classSqr = new Sqr();
        MyDoubleFunction anonymSqr = AnonymousClasses.sqr();
        MyDoubleFunction lambdaSqr = Lambdas.sqr();
        double testInput = 2.0;
        double expectedResult = 4.0;
        testDoubleEquals(expectedResult, metodSqr.apply(testInput));
        testDoubleEquals(expectedResult, classSqr.apply(testInput));
        testDoubleEquals(expectedResult, anonymSqr.apply(testInput));
        testDoubleEquals(expectedResult, lambdaSqr.apply(testInput));
        Node aPointX = () -> 0.0;
        Node aPointY = () -> 0.0;
        Node bPointX = () -> 3.0;
        Node bPointY = () -> 0.0;
        Node cPointX = () -> 3.0;
        Node cPointY = () -> 4.0;
        Node triangleAreaNode = Heron.heron(aPointX, aPointY, bPointX, bPointY, cPointX, cPointY);
        double areaResult = triangleAreaNode.evaluate();
        testDoubleEquals(6.0, areaResult);
        Node startResult = () -> 4.0;
        Node chainNode = MyChainedFunction.add2sqrt(startResult);
        double chainResult = chainNode.evaluate();
        double calculatedResult = Math.sqrt(4.0 + 1.0 + 1.0);
        testDoubleEquals(calculatedResult, chainResult);
        testDoubleEquals(2.4494, chainResult);
    }

    /**
     * Tests if the two supplied doubles are equal (with precision 0.0001).
     * If they are, the method returns normally.
     * If they are not equal, the method terminates the program printing the two values.
     *
     * @param expected the double value the result is expected to be
     * @param actual   the actually computed value to be tested
     */
    @DoNotTouch
    private static void testDoubleEquals(double expected, double actual) {
        double epsilon = 0.0001;
        StudentTestUtils.testWithinRange(expected - epsilon, expected + epsilon, actual);
    }

}
