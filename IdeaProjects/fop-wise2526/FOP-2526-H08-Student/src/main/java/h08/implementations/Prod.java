package h08.implementations;

import h08.functions.MyDoubleArrayFunction;

public class Prod implements MyDoubleArrayFunction {

    @Override
    public double apply(double[] degerler) {
        double carpimSonuc = 1.0;
        int sayac = 0;

        while (sayac < degerler.length) {
            carpimSonuc = carpimSonuc * degerler[sayac];
            sayac++;
    }
            return carpimSonuc;
    }
}
