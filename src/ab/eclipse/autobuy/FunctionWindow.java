package ab.eclipse.autobuy;



import ab.eclipse.EclipseFuntime;
import ab.eclipse.system.misc.SimpleFunction;
import ab.eclipse.utils.render.Render2d;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FunctionWindow {
    public double x, y, w, h;
    public List<FunctionButton> functionButtons = new ArrayList<>();

    public FunctionWindow(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        double y2 = y + 4;
        for (SimpleFunction simpleFunction : EclipseFuntime.functionList) {
            functionButtons.add(new FunctionButton(x, y2, w, 18, simpleFunction));
            y2 += 20;
        }
    }

    public void render(int mx, int my) {
        Render2d.drawRoundedRect(x, y, w, h, 4, new Color(39, 37, 37, 255));

        for (FunctionButton functionButton : functionButtons) {
            functionButton.render();
        }
    }

    public void click(double mx, double my, int b) {
        for (FunctionButton functionButton : functionButtons) {
            functionButton.click(mx, my, b);
        }
    }

    public void key(int key) {
        for (FunctionButton functionButton : functionButtons) {
            functionButton.key(key);
        }
    }
}