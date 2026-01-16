package h09.exceptions.packet;
import h09.packet.PacketType;
public class PacketTypeException extends PacketException {
    public PacketTypeException(PacketType expected, PacketType got) {
        super("Beklenen tip: " + expected + ", gelen tip: " + got);
    }
}
