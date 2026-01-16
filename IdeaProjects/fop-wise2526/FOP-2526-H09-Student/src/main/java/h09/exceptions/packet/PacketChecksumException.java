package h09.exceptions.packet;

public class PacketChecksumException extends PacketException {
    public PacketChecksumException(int expected, int got) {
        super("Beklenen checksum: " + expected + ", gelen: " + got);
    }
}
