package h09.tools;

import h09.device.TCPClient;
import h09.exceptions.InternetException;
import h09.exceptions.packet.PacketException;
import h09.exceptions.port.NoFreePortException;
import h09.exceptions.tcp.TCPException;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * Helper for simple TCP request/response calls.
 * Creates a client, connects, sends a string, reads the reply, then closes.
 * Prints short error messages on failure.
 */
@DoNotTouch
public class Requests {

    /**
     * Sends the given request to the server at the given port using a new client.
     * The reply from the server is returned. If an error occurs during transport
     * the error is caught and handled correspondingly. The created client is
     * finally closed regardless whether something went wrong or not.
     *
     * @param from    the server port to connect to
     * @param request the text to send
     * @return the server reply, or null on error
     */
    @StudentImplementationRequired("H9.5")
    public static String fetch(int from, String request) {
        TCPClient client = null;
        try {
            client = new TCPClient(from);
            client.connect();
            client.send(request);
            String answer = client.receive();
            return answer;
        } catch (NoFreePortException e) {
            printNoFreePort();
            return null;
        } catch (TCPException e) {
            printTCPExc(e);
            return null;
        } catch (PacketException e) {
            printPacketExc(e);
            return null;
        } catch (InternetException e) {
            printInternetExc(e);
            return null;
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }

    @DoNotTouch
    private static void printInternetExc(InternetException e) {
        System.out.println("Some other InternetException occurred: " + e.getMessage());
    }

    @DoNotTouch
    private static void printPacketExc(PacketException e) {
        System.out.println("Something went wrong with the packets! " + e.getMessage());
    }

    @DoNotTouch
    private static void printNoFreePort() {
        System.out.println("Could not register client on the internet, all ports are full!");
    }

    @DoNotTouch
    private static void printTCPExc(TCPException e) {
        System.out.println("Connection failed: " + e.getMessage());
    }

}
