package ab.eclipse.utils.animation;

import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class ColorTransfusion {
    private int r, g, b;

    public ColorTransfusion() {
        this(255, 255, 255);
    }

    public ColorTransfusion(Color color) {
        this(color.getRed(), color.getGreen(), color.getBlue());
    }

    public ColorTransfusion(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void animate(Color c, int speed) {
        int cr = c.getRed();
        int cg = c.getGreen();
        int cb = c.getBlue();

        if (r > cr) {
            r -= Math.min(speed, Math.max(cr, r) - Math.min(cr, r));
        }

        if (r < cr) {
            r += Math.min(speed, Math.max(cr, r) - Math.min(cr, r));
        }

        if (g > cg) {
            g -= Math.min(speed, Math.max(cg, g) - Math.min(cg, g));
        }

        if (g < cg) {
            g += Math.min(speed, Math.max(cg, g) - Math.min(cg, g));
        }

        if (b > cb) {
            b -= Math.min(speed, Math.max(cb, b) - Math.min(cb, b));
        }

        if (b < cb) {
            b += Math.min(speed, Math.max(cb, b) - Math.min(cb, b));
        }

        r = MathHelper.clamp(r, 0, 255);
        g = MathHelper.clamp(g, 0, 255);
        b = MathHelper.clamp(b, 0, 255);
    }


    public Color getColor() {
        return getColor(255);
    }

    public Color getColor(int a) {
        return new Color(r, g, b, a);
    }
}