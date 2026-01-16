package h09.utils;

import h09.exceptions.InternetException;
import h09.exceptions.tcp.TCPException;
import h09.exceptions.TimeoutException;
import h09.packet.Packet;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Utility class providing helper methods for TCP communication.
 * Contains methods for handling packet retrieval with retry logic.
 */
@DoNotTouch
public class TCPUtils {

    /**
     * Attempts to retrieve a packet up to three times, handling timeouts and old packets.
     * <p>
     * Example usage:
     * <pre>
     * {@code
     * Packet p = TCPUtils.try3Times(() -> {
     *     connection.sendPacket(sequence, DATA, "Hello");
     *     return connection.waitForPacketTimeout(5000);
     * }, sequence+1);
     * p.expectType(DATA);
     * assert p.getData().equals("World");
     * }
     * </pre>
     *
     * @param func         the function that attempts to retrieve a packet. may optionally also send a packet.
     * @param nextSequence the expected sequence number for the packet
     * @return the retrieved packet if successful
     * @throws InternetException if an error occurs during packet retrieval
     * @throws TCPException      if the maximum number of retries is exceeded
     */
    @DoNotTouch
    public static Packet try3Times(ThrowingPacketWaiter func, int nextSequence) throws InternetException {
        int tries = 0;
        while (tries < 3) {
            try {
                Packet p = func.tryGet();
                if (p.getHeader().sequenceNumber() < nextSequence) {
                    Verbose.out.println(Thread.currentThread().getName() + ": received old packet " + p.getHeader().sequenceNumber() + " < " + nextSequence);
                    continue;
                }
                return p;
            } catch (TimeoutException e) {
                Verbose.out.println(Thread.currentThread().getName() + ": timeout");
                tries++;
            }
        }
        throw new TCPException("unstable connection, too many timeouts!");
    }

}
