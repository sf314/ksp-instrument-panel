/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package krpcj.fsw;

import java.io.IOException;
import java.util.Scanner;

import krpc.client.Connection;
import krpc.client.services.KRPC;
import krpcj.fsw.config.Constants;
import krpcj.fsw.data.Telemetry;
import krpcj.fsw.ui.InstrumentPanel;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) throws Exception {
        System.out.println(new App().getGreeting());

        Scanner scanner = new Scanner(System.in);
        boolean endProg = false;

        InstrumentPanel instrumentPanel = setupInstrumentPanel();
        Telemetry telemetry = new Telemetry();

        // Scan for input continuously until quit
        while (!endProg) {

            // Skip if no input
            if (!scanner.hasNext()) {
                continue;
            }

            // Skip if input is not an integer
            if (!scanner.hasNextInt()) {
                String input = scanner.next(); // Dequeue whatever character was provided
                System.out.println("Disregarding: " + input);
                continue;
            }

            // Fetch integer
            int input = scanner.nextInt();
            System.out.println("Input: " + input);
            switch (input) {
            case 0:
                endProg = true;
                break;
            case 1:
                System.out.println("Connecting to KRPC server...");
                Connection conn = connectToServer();
                KRPC krpc = KRPC.newInstance(conn);
                System.out.println(krpc.getStatus().getVersion());
                break;
            case 2:
                System.out.println("Updating telemetry!");
                telemetry.setAirspeed(Math.random() * 160);
                instrumentPanel.updateTelem(telemetry);
                break;
            default:
                System.out.println("Unrecognized input: " + input);
            }
        }
        scanner.close();
        instrumentPanel.closeWindow();
    }

    protected static Connection connectToServer() throws Exception {
        try {
            Connection conn = Connection.newInstance(Constants.CLIENT_NAME, Constants.SERVER_IP, Constants.SERVER_RPC_PORT, Constants.SERVER_STREAM_PORT);
            return conn;
        } catch (IOException e) {
            throw new Exception("No suitable KRPC server running! " + e.getMessage(), e);
        } catch (Exception e) {
            throw new Exception ("Unknown issue when connecting to server!" + e.getMessage(), e);
        }
    }

    protected static InstrumentPanel setupInstrumentPanel() {
        InstrumentPanel instrumentPanel = new InstrumentPanel();
        return instrumentPanel;
    }
}
