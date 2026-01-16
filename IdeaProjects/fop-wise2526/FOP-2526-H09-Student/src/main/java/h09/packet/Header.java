package h09.packet;

import h09.connection.InternetPool;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Record representing the header of a packet.
 * Contains routing information and packet metadata.
 *
 * @param sourcePort      the port from which the packet originated
 * @param destinationPort the port to which the packet is destined
 * @param sequenceNumber  the sequence number of the packet for ordering
 * @param checksum        the checksum for data integrity verification
 * @param type            the type of the packet (SYN, DATA, ACK, CLOSE)
 */
@DoNotTouch
public record Header(int sourcePort,
                     int destinationPort,
                     int sequenceNumber,
                     int checksum,
                     PacketType type) {

    /**
     * Compact constructor for the Header record.
     * Validates that port numbers are within valid range and sequence number is positive.
     *
     * @throws AssertionError if either of the ports is not in the specified range or
     *                        the sequence number is non-positive
     */
    public Header {
        assert sourcePort >= 0 && sourcePort < InternetPool.MAX_PORT :
            "Source port is in an invalid range: " + sourcePort;
        assert destinationPort >= 0 && destinationPort < InternetPool.MAX_PORT :
            "Target port is in an invalid range.: " + destinationPort;
        assert sequenceNumber > 0 :
            "Sequence number must be positive.: " + sequenceNumber;
    }

    /**
     * Returns a string representation of the header.
     * Includes all header fields: sourcePort, destinationPort, sequenceNumber, checksum, and type.
     *
     * @return a string representation of the header
     */
    @Override
    @DoNotTouch
    public @NotNull String toString() {
        return "Header{" +
            "sourcePort=" + sourcePort +
            ", destinationPort=" + destinationPort +
            ", sequenceNumber=" + sequenceNumber +
            ", checksum=" + checksum +
            ", type=" + type +
            '}';
    }

}
