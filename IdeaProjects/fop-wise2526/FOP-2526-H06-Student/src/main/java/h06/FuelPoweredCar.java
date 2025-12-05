package h06;

public class FuelPoweredCar extends FuelPoweredVehicle {

    private static final double BASE_CONSUMPTION = 6.5;

    public FuelPoweredCar(FuelType fuelType, int fillingLevel) {
        super(fuelType, TransportType.CAR, fillingLevel);
    }

    @Override
    public double letMeMove(int distance) {
        if (motorRunning == false) {
            return 0.0;
        }

        double distanceKm = distance / 100000.0;
        double requiredFuel = distanceKm * BASE_CONSUMPTION;
        int consumption = (int) requiredFuel;

        int availableFuel = fillingLevel;
        if (consumption > availableFuel) {
            consumption = availableFuel;
        }

        fillingLevel = fillingLevel - consumption;

        return consumption;
    }
}
