package h06;

public class DrivableLawnMower extends FuelPoweredVehicle {
    protected boolean bladeSpinning;

    public DrivableLawnMower(FuelType fuelType, int fillingLevel) {
        super(fuelType, TransportType.CAR, fillingLevel);
        bladeSpinning = false;
    }

    public boolean isBladeSpinning() {
        return bladeSpinning;
    }

    public void spinBlade() {
        if (motorRunning == true) {
            bladeSpinning = true;
        }
    }

    public void stopBlade() {
        bladeSpinning = false;
    }

    @Override
    public double letMeMove(int distance) {
        if (motorRunning == false) {
            return 0.0;
        }

        double valeInKilometers = distance / 1000.0;
        double need = valeInKilometers * 0.5;
        int fuelConsumption = (int) need;

        int inTank = fillingLevel;
        if (fuelConsumption > inTank) {
            fuelConsumption = inTank;
        }

        fillingLevel = fillingLevel - fuelConsumption;

        return fuelConsumption;
    }
}
