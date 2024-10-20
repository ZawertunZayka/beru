package ab.eclipse.utils.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;

public class Render2d {
    public static Shader ROUNDED = new Shader(Shader.ROUNDED_FRAG);

    public static void drawRoundedRect(double x, double y, double width, double height, double radius, Color color) {
        float[] c = getColorComps(color);

        GlStateManager.disableTexture();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        ROUNDED.load();
        ROUNDED.setUniformf("size", (float)width * 2, (float)height * 2);
        ROUNDED.setUniformf("round", (float)radius * 2);
        ROUNDED.setUniformf("color", c[0], c[1], c[2], c[3]);
        Shader.draw(x, y, width, height);
        ROUNDED.unload();

        RenderSystem.defaultBlendFunc();
        GlStateManager.enableTexture();
        GlStateManager.disableBlend();
        GL11.glColor4f(1f, 1f, 1f, 1f);
    }

    public static float[] getColorComps(Color color) {
        return new float[] {color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f};
    }
}
