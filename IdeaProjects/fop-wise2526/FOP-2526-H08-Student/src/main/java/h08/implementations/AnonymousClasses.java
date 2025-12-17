package h08.implementations;

import h08.functions.MyDoubleBiFunction;
import h08.functions.MyDoubleFunction;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * Implementations of functional interfaces using anonymous classes
 */
@SuppressWarnings({"Convert2Lambda", "unused", "RedundantSuppression"})
public final class AnonymousClasses {

    @DoNotTouch
    private AnonymousClasses() {
        // do not instantiate helper class
    }

    /**
     * @return MyDoubleFunction implementation for x^2
     */
    @StudentImplementationRequired("H8.2.2")
    public static MyDoubleFunction sqr() {
        return new MyDoubleFunction() {
        @Override
            public double apply(double girisDegeri) {
                double kareSonuc = girisDegeri * girisDegeri;
                return kareSonuc;
            }
        };
    }

    /**
     * @return MyDoubleBiFunction implementation for x-y
     */
    @StudentImplementationRequired("H8.3.2")
    public static MyDoubleBiFunction sub() {
        return new MyDoubleBiFunction() {
            @Override
            public double apply(double birinciSayi, double ikinciSayi) {
                double cikarimSonucu = birinciSayi - ikinciSayi;
                return cikarimSonucu;
            }
        };
    }
}
