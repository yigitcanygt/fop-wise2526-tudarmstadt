package h09.exceptions.packet;

import h09.packet.Packet;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Exception thrown when attempting to access data in a {@link Packet} that doesn't have any.
 * This typically occurs when trying to get data from non-{@link Packet.Type#DATA DATA} type packets.
 */
@DoNotTouch
public class NoDataException extends PacketException {

    /**
     * Constructs a new {@code NoDataException} with a message indicating the packet type.
     *
     * @param packet the packet that doesn't contain data
     */
    @DoNotTouch
    public NoDataException(Packet packet) {
        super("No data in packet as this is a " + packet.getHeader().type() + " packet");
    }
}
