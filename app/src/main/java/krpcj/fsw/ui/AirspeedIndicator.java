package krpcj.fsw.ui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import krpcj.fsw.ui.graph.CartPoint;
import krpcj.fsw.ui.graph.PolarPoint;
import krpcj.fsw.util.GraphUtil;

public class AirspeedIndicator extends JPanel {
    private double value;
    private JLabel valueDisplay;

    /**
     * Constructor. Sets up the initial values and panel border.
     */
    public AirspeedIndicator() {
        super();
        this.value = 0;
        this.valueDisplay = new JLabel();
        valueDisplay.setText("Airspeed");

        // Setup display of value
        this.add(valueDisplay);

        this.setVisible(true);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    /**
     * Update the airspeed, so it will display on the next paint cycle.
     * @param newValue
     */
    public void updateValue(double newValue) {
        this.value = newValue;
        String dispString = String.format("Airspeed: %.2f", this.value);
        this.valueDisplay.setText(dispString);
    }

    /**
     * Draw the airspeed indicator
     */
    @Override
    protected void paintComponent(Graphics graphics) {
        int frameWidth = this.getWidth();
        int frameHeight = this.getHeight();
        int padding = 10; // Distance from border

        // Circle properties
        int radius = (Math.min(frameWidth, frameHeight) / 2) - padding;
        int centerX = frameWidth / 2;
        int centerY = frameHeight / 2;

        // Graphics properties
        int x = centerX - radius;
        int y = centerY - radius;
        int diameter = radius * 2;

        // Draw gauge cirle, centered in the panel
        graphics.setColor(Color.BLACK);
        graphics.fillOval(x, y, diameter, diameter);

        // Draw gauge dashes
        drawDash(45,       radius / 3, "40", graphics, centerX, centerY, radius);
            drawDash(23,   radius / 6, "", graphics, centerX, centerY, radius);
        drawDash(0,        radius / 3, "80", graphics, centerX, centerY, radius);
            drawDash(-23,  radius / 6, "", graphics, centerX, centerY, radius);
        drawDash(-45,      radius / 3, "120", graphics, centerX, centerY, radius);
            drawDash(-68,  radius / 6, "", graphics, centerX, centerY, radius);
        drawDash(-90,      radius / 3, "160", graphics, centerX, centerY, radius);
            drawDash(-113, radius / 6, "", graphics, centerX, centerY, radius);

        drawDash(-135,     radius / 3, "200", graphics, centerX, centerY, radius);
            drawDash(-158, radius / 3, "", graphics, centerX, centerY, radius);
        drawDash(-180,     radius / 3, "300", graphics, centerX, centerY, radius);
            drawDash(-203, radius / 3, "", graphics, centerX, centerY, radius);
        drawDash(-225,     radius / 3, "400", graphics, centerX, centerY, radius);

        // Draw indicator needle
        // drawDash(this.airspeedToAngleDeg(this.value), radius, "", graphics, centerX, centerY, radius);

        // Needle?
        int needleDeg = this.airspeedToAngleDeg(this.value);
        PolarPoint tip = new PolarPoint(Math.toRadians(needleDeg), radius);
        PolarPoint left = new PolarPoint(Math.toRadians(needleDeg + 90), (double)radius * 0.05);
        PolarPoint right = new PolarPoint(Math.toRadians(needleDeg - 90), (double)radius * 0.05);
        PolarPoint bottom = new PolarPoint(Math.toRadians(needleDeg + 180), (double)radius * 0.05);

        CartPoint tipCart = GraphUtil.polarToCart(tip);
        CartPoint leftCart = GraphUtil.polarToCart(left);
        CartPoint rightCart = GraphUtil.polarToCart(right);
        CartPoint bottomCart = GraphUtil.polarToCart(bottom);

        int[] xCoords = {centerX + tipCart.x, centerX + leftCart.x, centerX + bottomCart.x, centerX + rightCart.x};
        int[] yCoords = {centerY - tipCart.y, centerY - leftCart.y, centerY - bottomCart.y, centerY - rightCart.y};
        graphics.fillPolygon(xCoords, yCoords, 4);
    }

    /**
     * Utility function to draw the gauge dashes, optionally with labels.
     * @param angleDegrees Angle of the dash from the center of the gauge. Speed of 0kts is 90deg.
     * @param length Length of the dash
     * @param label An optional speed label for the dash, positioned on the inner radius.
     * @param graphics Pass in graphics context for painting.
     * @param centerX Pass in X coordinate of the center of the panel.
     * @param centerY Pass in Y coordinate of the center of the panel.
     * @param radius Pass in radius of the gauge.
     */
    protected void drawDash(int angleDegrees, int length, String label, Graphics graphics, int centerX, int centerY, int radius) {
        PolarPoint polarStart = new PolarPoint(Math.toRadians(angleDegrees), radius);
        PolarPoint polarEnd = new PolarPoint(Math.toRadians(angleDegrees), radius - length);
        CartPoint dashStart = GraphUtil.polarToCart(polarStart);
        CartPoint dashEnd = GraphUtil.polarToCart(polarEnd);

        graphics.setColor(Color.WHITE);

        // Note: minus y, because y axis is flipped in JPanel: JPanel coords start in top left corner
        graphics.drawLine(
            centerX + dashStart.x,
            centerY - dashStart.y,
            centerX + dashEnd.x,
            centerY - dashEnd.y);

        // Only draw the label if provided
        if (label == null || label.isBlank()) {
            return;
        }

        PolarPoint labelPolar = new PolarPoint(Math.toRadians(angleDegrees), radius - length - 10);
        CartPoint labelCoords = GraphUtil.polarToCart(labelPolar);
        graphics.drawString(label,
            centerX + labelCoords.x - (label.length() * 10 / 2),
            centerY - labelCoords.y);
    }

    /**
     * Convert an airspeed value to the angle that the needle should point on
     * the gauge. Note that the scale of the gauge changes above 200kts.
     * @param airspeed The airspeed, in knots.
     * @return Angle in degrees.
     */
    protected int airspeedToAngleDeg(double airspeed) {
        // Convert airspeed to the indicator needle's angle in degrees
        if (airspeed <= 0.0) {
            return 90;
        }

        if (airspeed >= 0.0 && airspeed <= 200.0) {
            return (int)(-1.125 * airspeed) + 90;
        }

        if (airspeed >= 200.0) {
            return (int)(-0.45 * airspeed) - 45;
        }

        return 90; // Should never happen
    }
}
