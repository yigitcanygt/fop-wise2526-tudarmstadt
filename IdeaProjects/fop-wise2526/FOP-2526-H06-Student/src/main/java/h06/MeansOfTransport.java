package h06;

public abstract class MeansOfTransport {
    protected final TransportType transportType;
    public MeansOfTransport(TransportType transportType) {
        this.transportType = transportType;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public abstract double letMeMove(int distance);
}
