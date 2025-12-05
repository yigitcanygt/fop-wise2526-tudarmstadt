package h06;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * Utility class to calculate fuel taxes
 */
public final class TaxCalculator {

    @DoNotTouch
    private TaxCalculator() {
        // utility class should not be instantiated
    }

    /**
     * Calculates an imaginary tax for fuel powered devices.
     * The standard tax rate is 20%.
     * Hybrid devices get a discount related to their fuel/electricity ratio.
     *
     * @param device the fuel powered device to calculate taxes for
     * @param liters the amount of fuel in liters
     * @return the tax to be paid in Euros
     */
    @StudentImplementationRequired("H6.5")
    public static double calculateTax(FuelPowered device, double liters) {
        FuelType fuelType = device.getFuelType();
        double literPrice = 0.0;

        if (fuelType == FuelType.GASOLINE) {
            literPrice = 1.72;
        }
        if (fuelType == FuelType.DIESEL) {
            literPrice = 1.60;
        }
        if (fuelType == FuelType.LPG) {
            literPrice = 1.05;
        }

        double totalPrice = liters * literPrice;
        double taxRate = 0.2;

        boolean isHybrid = device instanceof HybridPowered;
        if (isHybrid) {
            HybridPowered hybridDevice = (HybridPowered) device;
            double rate = hybridDevice.getFuelElectricityRatio();
            taxRate = taxRate * rate;
        }

        double tax = totalPrice * taxRate;
        return tax;
    }
}
