package h06;

public abstract class FuelPoweredVehicle extends MeansOfTransport implements FuelPowered {
    private final FuelType fuelType;
    protected int fillingLevel;
    protected boolean motorRunning;

    public FuelPoweredVehicle(FuelType fuelType, TransportType transportType, int fillingLevel) {
        super(transportType);
        this.fuelType = fuelType;
        this.fillingLevel = fillingLevel;
        this.motorRunning = false;
    }

    @Override
    public FuelType getFuelType() {
        return fuelType;
    }

    public int getFillingLevel() {
        return fillingLevel;
    }

    public void fillUp(int fillValue) {
        if (fillValue > 0) {
            fillingLevel += fillValue;
        }
    }

    public void turnOn() {
        motorRunning = true;
    }

    public void turnOff() {
        motorRunning = false;
    }
}
