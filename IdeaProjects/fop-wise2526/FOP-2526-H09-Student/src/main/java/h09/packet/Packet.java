package h09.packet;

import h09.exceptions.packet.*;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * Represents data that can be sent over the network.
 * A packet consists of a mandatory header and optional data.
 */
@DoNotTouch
public class Packet {


    /**
     * Packet header containing control information.
     */
    @DoNotTouch
    @NotNull
    private Header header;

    /**
     * Packet data consisting of simple text.
     */
    @DoNotTouch
    private String data;

    /**
     * Constructs a new packet using the provided header and data.
     *
     * @param header the header of the packet
     * @param data   the data of the packet
     */
    @DoNotTouch
    public Packet(@NotNull Header header, String data) {
        this.header = header;
        this.data = data;
    }

    /**
     * Constructs a new {@link Packet}, using the provided information to construct a
     * {@link Header}, and {@link String data}.
     *
     * @param sourcePort      the address the packet came from
     * @param destinationPort the address the packet is going to
     * @param sequenceNumber  the sequence number for packet ordering
     * @param type            the packet type
     * @param data            the data of the packet or null if the packet has no data.
     *                        should at most be 8 characters
     * @throws AssertionError if the type is DATA but the provided data is null,
     *                        the provided data is not null but the type is not DATA or
     *                        the length of data (if present) is bigger than allowed.
     */
    public Packet(
        int sourcePort,
        int destinationPort,
        int sequenceNumber,
        PacketType type,
        String data) {
        assert type != PacketType.DATA || data != null :
            "Data is required for the DATA type";
        assert type == PacketType.DATA || data == null :
            "There should be no data for types that are not DATA";
        assert data == null || data.length() <= 8 :
            "The data length should be a maximum of 8 characters";
        this.data = data;
        int calculatedCheckSum = calculateChecksum(sequenceNumber, data);
        this.header = new Header(
            sourcePort,
            destinationPort,
            sequenceNumber,
            calculatedCheckSum,
            type
        );
    }

    /**
     * Calculates the checksum of a header using the sequence number and data.
     * Asserts data is not null for data packets, and data stays within the allowed length.
     *
     * @param seq  The sequence number of the packet
     * @param data The data of the packet
     * @return the calculated checksum
     */
    @DoNotTouch
    private static int calculateChecksum(int seq, String data) {
        return seq + (data == null ? 0 : data.length());
    }

    /**
     * Returns the header of the packet.
     *
     * @return the packet header
     */
    @DoNotTouch
    public @NotNull Header getHeader() {
        return header;
    }

    /**
     * Returns the data of the packet.
     *
     * @return the packet data
     * @throws NoDataException when there's no data in this packet
     */
    @DoNotTouch
    public String getData() throws NoDataException {
        if (data != null) {
            return data;
        } else {
            throw new NoDataException(this);
        }
    }

    /**
     * Validates that the {@link PacketType type} of the header of this packet is
     * the expected type. Else throws an exception.
     *
     * @param type the expected packet type
     */
    @StudentImplementationRequired("H9.3")
    public void expectType(PacketType type) throws PacketTypeException {
        PacketType incomingType = this.header.type();
        if (incomingType != type) {
            throw new PacketTypeException(type, incomingType);
        }
    }

    /**
     * Validates that the sequence number of the header of this packet
     * is the expected sequence number. Else throws an exception.
     *
     * @param seq the expected packet sequence number
     */
    @StudentImplementationRequired("H9.3")
    public void expectSequenceNumber(int seq) throws PacketSequenceException {
        int incomingSeq = this.header.sequenceNumber();
        if (incomingSeq != seq) {
            throw new PacketSequenceException(seq, incomingSeq);
        }
    }

    /**
     * Validates the checksum of the packet.
     * Compares the checksum in the header with a calculated checksum based on sequence number and data.
     */
    @StudentImplementationRequired("H9.3")
    public void validateChecksum() throws PacketChecksumException {
        int expectedChecksum = calculateChecksum(this.header.sequenceNumber(), this.data);
        int incomingChecksum = this.header.checksum();
        if (incomingChecksum != expectedChecksum) {
            throw new PacketChecksumException(expectedChecksum, incomingChecksum);
        }
    }

    /**
     * Returns a string representation of the packet.
     * Includes both header information and data content.
     *
     * @return a string representation of the packet
     */
    @Override
    @DoNotTouch
    public String toString() {
        return "Packet{" +
            "header=" + header +
            ", data='" + data + '\'' +
            '}';
    }
}
