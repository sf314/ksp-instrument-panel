package krpcj.fsw.computer;

import krpcj.fsw.data.Telemetry;
import krpcj.fsw.driver.KrpcDriver;

public class FlightComputer {

    private KrpcDriver driver;
    private FlightDataRecorder fdr;

    // Default constructor
    public FlightComputer() {
        if ("SIM".equals(System.getenv("FSW_ENV"))) {
            // SIMULATION - Only instantiate driver on real flights
            return;
        }

        this.driver = new KrpcDriver();
        this.driver.initDriver();
    }

    // Test constructor
    public FlightComputer(KrpcDriver driver, FlightDataRecorder fdr) {
        this.driver = driver;
        this.fdr = fdr;
    }

    public void powerOff() {
        if ("SIM".equals(System.getenv("FSW_ENV"))) {
            return;
        }

        // i.e. from UI control
        this.driver.disconnect();
    }

    public void powerOn() {
        if ("SIM".equals(System.getenv("FSW_ENV"))) {
            return;
        }

        // i.e. from UI control
        this.driver.initDriver();
    }

    public Telemetry getTelemetrySnapshot() {
        Telemetry telem = new Telemetry();

        if ("SIM".equals(System.getenv("FSW_ENV"))) {
            // SIMULATION
            long millis = System.currentTimeMillis();
            double secs = Double.valueOf(millis) / 1000.0;

            telem.setAltitude(Math.sin(secs) * 500 + 1000); // (500, 1500)
            telem.setAirspeed(Math.sin(secs) * 50 + 150); // (100, 200)
            telem.setvSpeed(Math.sin(secs) * 15); // (-15, 15)
            telem.setPitch(Math.sin(secs) * 45); // (-45, 45)
            telem.setRoll(Math.sin(secs) * 45); // (-45, 45)
            telem.setHeading(Math.sin(secs) * 180 + 180); // (0, 360)
            return telem;
        }

        // Read telemetry from driver
        telem.setAirspeed(this.driver.getSpeed());
        telem.setPitch(this.driver.getPitch());
        telem.setRoll(this.driver.getRoll());
        telem.setAltitude(this.driver.getASL());
        telem.setHeading(this.driver.getHeading());
        telem.setvSpeed(this.driver.getVerticalSpeed());

        // Save current snapshot to FDR
        return telem;
    }
}
