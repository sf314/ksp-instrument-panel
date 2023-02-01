package krpcj.fsw.util;

import krpcj.fsw.ui.graph.CartPoint;
import krpcj.fsw.ui.graph.PolarPoint;

public class GraphUtil {

    /**
     * Convert the given polar coordinate into cartesian coordinates. Note that
     * it is assumed that 0deg is to the right, and 90deg is up. Thus, the
     * corresponding cartesian y-axis points up as well.
     * @param polar
     * @return
     */
    public static CartPoint polarToCart(PolarPoint polar) {
        int x = (int)(polar.r * Math.cos(polar.theta));
        int y = (int)(polar.r * Math.sin(polar.theta));
        return new CartPoint(x, y);
    }
}
