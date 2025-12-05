package h06;

public interface HybridPowered extends ElectricallyPowered, FuelPowered {
    double getFuelElectricityRatio();
}
