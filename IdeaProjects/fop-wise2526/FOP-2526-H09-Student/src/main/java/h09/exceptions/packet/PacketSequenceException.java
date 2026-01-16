package h09.exceptions.packet;

public class PacketSequenceException extends PacketException {
    public PacketSequenceException(int expected, int got) {
        super("Beklenen sira numarasi: " + expected + ", gelen: " + got);
    }
}
