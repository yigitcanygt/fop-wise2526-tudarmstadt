package h09;

import h09.connection.InternetPool;
import h09.connection.LossyChannel;
import h09.connection.TimeoutChannel;
import h09.device.TCPClient;
import h09.device.TCPServer;
import h09.tools.Requests;
import h09.utils.Verbose;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Main entry point in executing the program.
 */
public class Main {

    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments
     */
    public static void main(String[] args) {
        // Verbose.turnVerbose();
        // InternetPool.setChannel(new LossyChannel());
        // InternetPool.setChannel(new TimeoutChannel());
        try {
            TCPServer server = new TCPServer(443);

            // Client Version
            TCPClient client = new TCPClient(443);
            client.connect();
            client.send("Hello Server");
            String req = client.receive();
            client.close();

            // Requests Version
            // String req = Requests.fetch(443, "Hello Server");

            server.close();
            System.out.println(req);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
