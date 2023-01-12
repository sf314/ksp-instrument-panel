package krpcj.fsw.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class AirspeedIndicator extends JPanel {
    private double value;

    private JLabel valueDisplay;

    public AirspeedIndicator() {
        super();
        this.value = 0;
        this.valueDisplay = new JLabel();
        valueDisplay.setText("Airspeed");

        // Setup display of value
        this.add(valueDisplay);

        this.setVisible(true);
    }

    public void updateValue(double newValue) {
        this.value = newValue;
        String dispString = String.format("Airspeed: %.2f", this.value);
        this.valueDisplay.setText(dispString);
    }
}
