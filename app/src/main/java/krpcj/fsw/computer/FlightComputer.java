package krpcj.fsw.computer;

import krpcj.fsw.data.Telemetry;
import krpcj.fsw.driver.KrpcDriver;

public class FlightComputer {

    private KrpcDriver driver;
    private FlightDataRecorder fdr;

    // Default constructor
    public FlightComputer() {

    }

    // Test constructor
    public FlightComputer(KrpcDriver driver, FlightDataRecorder fdr) {
        this.driver = driver;
        this.fdr = fdr;
    }

    public Telemetry getTelemetrySnapshot() {
        // Read telemetry from driver

        // Save current snapshot to FDR
        return null;
    }
}
