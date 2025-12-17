package h08.implementations;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * Implementations of math operations using ordinary methods which can then be referenced
 */
public final class Methods {

    @DoNotTouch
    private Methods() {
        // do not instantiate helper class
    }

    /**
     * Squares x
     *
     * @param x value to be squared
     * @return x^2
     */
    @StudentImplementationRequired("H8.2.2")
    public static double sqr(double x) {
        double sonuc = x * x;
        return sonuc;
    }

    /**
     * Calculates x - y
     *
     * @param x subtrahend
     * @param y minuend
     * @return x - y
     */
    @StudentImplementationRequired("H8.3.2")
    public static double sub(double x, double y) {
        double fark = x - y;
        return fark;
    }


    /**
     * Computes the n-ary product of all input values
     *
     * @param x values to be multiplied
     * @return x_0 * ... x_n or 1, if x is empty
     */
    @StudentImplementationRequired("H8.4.2")
    public static double prod(double[] x) {
        double carpim = 1.0;
        int indeks = 0;
        while (indeks < x.length) {
            carpim = carpim * x[indeks];
            indeks = indeks + 1;
        }
        return carpim;
    }
}
