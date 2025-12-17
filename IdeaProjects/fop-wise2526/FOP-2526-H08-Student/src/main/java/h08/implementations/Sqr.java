package h08.implementations;

import h08.functions.MyDoubleFunction;

public class Sqr implements MyDoubleFunction {

    @Override
    public double apply(double deger) {
        double kare = deger * deger;
        return kare;
    }
}
