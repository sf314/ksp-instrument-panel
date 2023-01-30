package krpcj.fsw.computer;

import krpcj.fsw.data.Telemetry;
import krpcj.fsw.driver.KrpcDriver;

public class FlightComputer {

    private KrpcDriver driver;
    private FlightDataRecorder fdr;

    // Default constructor
    public FlightComputer() {
        if (System.getenv("FSW_ENV").equals("SIM")) {
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
        // i.e. from UI control
        this.driver.disconnect();
    }

    public void powerOn() {
        // i.e. from UI control
        this.driver.initDriver();
    }

    public Telemetry getTelemetrySnapshot() {
        Telemetry telem = new Telemetry();

        if (System.getenv("FSW_ENV").equals("SIM")) {
            // SIMULATION
            telem.setAltitude(Math.random() * 10000);
            telem.setAirspeed(Math.random() * 200.0);
            telem.setvSpeed(Math.random() * 30 - 15.0);
            telem.setPitch(Math.random() * 90 - 45.0);
            telem.setRoll(Math.random() * 90 - 45.0);
            return telem;
        }

        // Read telemetry from driver
        telem.setAltitude(this.driver.getASL());
        telem.setAirspeed(this.driver.getSpeed());
        telem.setvSpeed(this.driver.getVerticalSpeed());
        telem.setPitch(this.driver.getPitch());
        telem.setRoll(this.driver.getRoll());

        // Save current snapshot to FDR
        return telem;
    }
}
