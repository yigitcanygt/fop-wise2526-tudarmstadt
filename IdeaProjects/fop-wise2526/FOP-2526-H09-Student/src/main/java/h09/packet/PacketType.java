package h09.packet;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Enum representing the different types of packets in the TCP-like protocol.
 * Each type serves a specific purpose in the connection lifecycle.
 */
@DoNotTouch
public enum PacketType {
    /**
     * Synchronization packet used to establish a connection.
     */
    @DoNotTouch
    SYN,

    /**
     * Data packet containing actual information to be transmitted.
     */
    @DoNotTouch
    DATA,

    /**
     * Acknowledgment packet confirming receipt of previous packets.
     */
    @DoNotTouch
    ACK,

    /**
     * Close packet indicating the termination of a connection.
     */
    @DoNotTouch
    CLOSE
}
