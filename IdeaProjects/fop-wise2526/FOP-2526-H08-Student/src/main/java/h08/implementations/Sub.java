package h08.implementations;

import h08.functions.MyDoubleBiFunction;

public class Sub implements MyDoubleBiFunction {

    @Override
    public double apply(double ilkDeger, double ikinciDeger) {
        double farkSonuc = ilkDeger - ikinciDeger;
        return farkSonuc;
    }
}
