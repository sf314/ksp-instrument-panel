package krpcj.fsw.computer;

import krpcj.fsw.data.Telemetry;
import krpcj.fsw.driver.KrpcDriver;

public class FlightComputer {

    private KrpcDriver driver;
    private FlightDataRecorder fdr;

    // Default constructor
    public FlightComputer() {
        // TODO: DISABLE AFTER TESTING
        // this.driver = new KrpcDriver();
        // this.driver.initDriver();
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
        // Read telemetry from driver
        Telemetry telem = new Telemetry();
        // telem.setAltitude(this.driver.getASL());
        // telem.setvSpeed(this.driver.getVerticalSpeed());

        // TODO: DISABLE AFTER TESTING
        telem.setAltitude(Math.random() * 10000);
        telem.setAirspeed(Math.random() * 200.0);
        telem.setvSpeed(Math.random() * 30 - 15.0);
        telem.setPitch(Math.random() * 90 - 45.0);
        telem.setRoll(Math.random() * 90 - 45.0);

        // Save current snapshot to FDR
        return telem;
    }
}
