package krpcj.fsw.ui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AttitudeIndicator extends JPanel {
    // (0,0) represents level flight
    private double pitch; // -90° down, +90° up
    private double roll; // -180° inverted left, +180° inverted right

    private JLabel valueDisplay;

    public AttitudeIndicator() {
        super();

        this.pitch = 0;
        this.roll = 0;
        this.valueDisplay = new JLabel();
        valueDisplay.setText("Attitude Indicator");

        // Setup display of value
        this.add(valueDisplay);

        this.setVisible(true);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public void updateValue(double newPitch, double newRoll) {
        this.pitch = newPitch;
        this.roll = newRoll;
        String dispString = String.format("Attitude: %.1f pitch, %.1f roll", this.pitch, this.roll);
        this.valueDisplay.setText(dispString);
    }
}
