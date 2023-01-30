package krpcj.fsw.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TextIndicator extends JPanel {
    private double value;
    private String name;

    private JLabel valueDisplay;

    public TextIndicator(String name) {
        super();
        this.value = 0;
        this.name = name;
        this.valueDisplay = new JLabel();
        valueDisplay.setText(name);

        // Setup display of value
        this.add(valueDisplay);

        this.setVisible(true);
    }

    public void updateValue(double newValue) {
        this.value = newValue;
        String dispString = String.format("%s: %.2f", this.name, this.value);
        this.valueDisplay.setText(dispString);
    }
}
