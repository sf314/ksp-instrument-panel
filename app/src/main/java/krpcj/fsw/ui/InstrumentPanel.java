package krpcj.fsw.ui;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

import krpcj.fsw.computer.FlightComputer;
import krpcj.fsw.data.Telemetry;

/**
 * Draw an instrument panel to display live flight telemetry and provide easy
 * access to controls.
 */
public class InstrumentPanel {

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

    // Computer
    private FlightComputer flightComputer;

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
    }

    /**
     * Close the instrument panel
     */
    public void closeWindow() {
        this.frame.dispose();
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
        frame.add(new JLabel("3. Altitude: 1300ft"));

        frame.add(new JLabel("4. Compass: 160°"));
        frame.add(new JLabel("5. V Spd: +500f/m"));
        frame.add(new JLabel("6. ILS: |__"));

        frame.add(new JLabel("7. "));
        frame.add(new JLabel("8. "));
        frame.add(new JLabel("9. "));
    }
}
