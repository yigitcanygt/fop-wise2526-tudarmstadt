package h06;

public class HybridLawnMower extends DrivableLawnMower implements HybridPowered {

    public HybridLawnMower(FuelType fuelType, int fillingLevel) {
        super(fuelType, fillingLevel);
    }

    @Override
    public void use(int duration) {
        int distance = 3 * duration;
        letMeMove(distance);
    }

    @Override
    public PlugType getSupportedPlugType() {
        return PlugType.HOME_OUTLET;
    }

    @Override
    public double getFuelElectricityRatio() {
        return 0.9;
    }
}
