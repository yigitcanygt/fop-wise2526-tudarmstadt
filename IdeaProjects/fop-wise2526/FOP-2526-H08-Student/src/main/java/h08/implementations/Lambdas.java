package h08.implementations;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;
import h08.functions.MyDoubleFunction;
import h08.functions.MyDoubleBiFunction;
import h08.functions.MyDoubleArrayFunction;
import static org.tudalgo.algoutils.student.Student.crash;

/**
 * Implementations of functional interfaces using lambdas
 */
@SuppressWarnings({"CodeBlock2Expr", "unused", "RedundantSuppression"})
public final class Lambdas {

    @DoNotTouch
    private Lambdas() {
        // do not instantiate helper class
    }

    /**
     * @return MyDoubleFunction implementation for x^2
     */
    @StudentImplementationRequired("H8.2.2")
    public static MyDoubleFunction sqr() {
      return giris -> giris * giris;
    }

    /**
     * @return MyDoubleFunction implementation for x-y
     */
    @StudentImplementationRequired("H8.3.2")
    public static MyDoubleBiFunction sub() {
       return (double sol, double sag) -> {
           double sonucDeger = sol - sag;
           return sonucDeger;
       };
    }

    /**
     * @return MyDoubleArrayFunction implementation for x_0 * ... * x_n
     */
    @StudentImplementationRequired("H8.4.2")
    public static MyDoubleArrayFunction prod() {
        return (double[] sayilar) -> {
            double toplam = 1.0;
            int pozisyon = 0;
            while (pozisyon < sayilar.length) {
                toplam = toplam * sayilar[pozisyon];
                pozisyon = pozisyon + 1;
            }
            return toplam;
        };
    }
}
