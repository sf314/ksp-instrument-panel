package krpcj.fsw.util;

import krpcj.fsw.ui.graph.CartPoint;
import krpcj.fsw.ui.graph.PolarPoint;

public class GraphUtil {

    public static CartPoint polarToCart(PolarPoint polar) {
        int x = (int)(polar.r * Math.cos(polar.theta));
        int y = (int)(polar.r * Math.sin(polar.theta));
        return new CartPoint(x, y);
    }
}
