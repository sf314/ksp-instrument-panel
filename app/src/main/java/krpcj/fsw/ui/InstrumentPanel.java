package krpcj.fsw.ui;

import java.awt.GridLayout;
import java.util.concurrent.CompletableFuture;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import krpcj.fsw.computer.FlightComputer;
import krpcj.fsw.data.Telemetry;

/**
 * Draw an instrument panel to display live flight telemetry and provide easy
 * access to controls.
 */
public class InstrumentPanel {

    private static final Logger logger = LoggerFactory.getLogger(InstrumentPanel.class.getSimpleName());

    // Frame props
    private static final String WINDOW_TITLE = "KRPC-J Instrument Panel";
    private static final int FRAME_WIDTH = 1200;
    private static final int FRAME_HEIGHT = 800;
    private JFrame frame;

    // Telem
    private Telemetry telemSnapshot;

    // Panels
    private AirspeedIndicator airspeedIndicator;
    private AttitudeIndicator attitudeIndicator;
    private TextIndicator altimeter;
    private TextIndicator compass;
    private TextIndicator vsi;

    // Computer
    private FlightComputer flightComputer;

    // Update loop
    CompletableFuture<Void> updateLoop;
    boolean isActive;

    /**
     * Constructor: Initialize, configure, and display the frame.
     */
    public InstrumentPanel() {
        // Init frame
        this.frame = this.initFrame();

        // Setup layout
        this.initLayout(this.frame);

        // Add panels
        this.addPanels(this.frame);

        // Display frame
        this.frame.setVisible(true);

        // Initialize flight computer
        this.flightComputer = new FlightComputer();

        this.isActive = true;
        updateLoop = CompletableFuture.runAsync(() -> {
            while (this.isActive) {
                try {
                    this.updateTelem();
                    Thread.sleep(100);
                } catch (Exception e) {
                    logger.error("Failed update loop: " + e.getMessage(), e);
                }
            }
        });
        logger.info("Instruments successfully initialized!");
    }

    /**
     * Close the instrument panel
     */
    public void closeWindow() {
        logger.info("Closing window...");
        this.isActive = false;
        this.flightComputer.powerOff();
        this.updateLoop.cancel(true);
        this.frame.dispose();
        logger.info("Window successfully closed!");
    }

    /**
     * Update the instrument panel data with the provided telemetry
     * @param telem
     */
    public void updateTelem() {
        // Save for later reference
        this.telemSnapshot = this.flightComputer.getTelemetrySnapshot();

        // Update all gauges
        this.airspeedIndicator.updateValue(this.telemSnapshot.getAirspeed());
        this.attitudeIndicator.updateValue(this.telemSnapshot.getPitch(), this.telemSnapshot.getRoll());
        this.altimeter.updateValue(this.telemSnapshot.getAltitude());
        this.compass.updateValue(this.telemSnapshot.getHeading());
        this.vsi.updateValue(this.telemSnapshot.getvSpeed());

        // Refresh!
        this.frame.invalidate();
        this.frame.validate();
        this.frame.repaint();
    }

    /**
     * Initialize the frame using the default properties.
     * @return
     */
    protected JFrame initFrame() {
        JFrame frame = new JFrame(WINDOW_TITLE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLayout(null); // No layout manager currently
        return frame;
    }

    /**
     * Initialize the given frame's layout via LayoutManager
     * @param frame
     */
    protected void initLayout(JFrame frame) {
        GridLayout manager = new GridLayout(3, 3);
        manager.setHgap(10);
        manager.setVgap(10);
        frame.setLayout(manager);
    }

    /**
     * Add instrument panels to the provided frame.
     * @param frame
     */
    protected void addPanels(JFrame frame) {
        this.airspeedIndicator = new AirspeedIndicator();
        frame.add(airspeedIndicator);

        this.attitudeIndicator = new AttitudeIndicator();
        frame.add(attitudeIndicator);

        this.altimeter = new TextIndicator("Altimeter");
        frame.add(this.altimeter);

        this.compass = new TextIndicator("Compass");
        frame.add(this.compass);

        this.vsi = new TextIndicator("VSI");
        frame.add(this.vsi);

        frame.add(new JLabel("6. ILS: |__"));

        frame.add(new JLabel("7. "));
        frame.add(new JLabel("8. "));
        frame.add(new JLabel("9. "));
    }
}
