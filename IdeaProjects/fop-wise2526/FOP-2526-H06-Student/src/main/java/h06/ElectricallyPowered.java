package h06;

public interface ElectricallyPowered {
    void use(int duration);
    PlugType getSupportedPlugType();
}
