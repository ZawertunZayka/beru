package ab.eclipse.utils.render;

import com.mojang.blaze3d.platform.GlStateManager;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;
import java.nio.FloatBuffer;
import java.util.stream.Collectors;

import static ab.eclipse.EclipseFuntime.mc;
import static org.lwjgl.opengl.GL11C.GL_QUADS;

public class Shader {
    public static final String BLOOM_FRAG = "https://raw.githubusercontent.com/sxmurxy2005/2D-Render-Util-1.16/forge-1.16.5/src/main/resources/assets/renderutil/shaders/bloom.frag";
    public static final String BLUR_FRAG = "https://raw.githubusercontent.com/sxmurxy2005/2D-Render-Util-1.16/forge-1.16.5/src/main/resources/assets/renderutil/shaders/blur.frag";
    public static final String ROUNDED_FRAG = "https://raw.githubusercontent.com/sxmurxy2005/2D-Render-Util-1.16/forge-1.16.5/src/main/resources/assets/renderutil/shaders/rounded.frag";
    public static final String ROUNDED_TEXTURE_FRAG = "https://raw.githubusercontent.com/sxmurxy2005/2D-Render-Util-1.16/forge-1.16.5/src/main/resources/assets/renderutil/shaders/rounded_texture.frag";
    public static final String VERTEX_VERT = "https://raw.githubusercontent.com/sxmurxy2005/2D-Render-Util-1.16/forge-1.16.5/src/main/resources/assets/renderutil/shaders/vertex.vert";
    public static final String BACK_FRAG = "https://raw.githubusercontent.com/Ranele/k/main/fes";
    public static final String GAUSSIAN_BLOOM_SHADER = "https://raw.githubusercontent.com/Ranele/k/main/gbs";

    public static final int VERTEX_SHADER;
    private final int programId;

    static {
        VERTEX_SHADER = GlStateManager.createShader(GL30.GL_VERTEX_SHADER);
        GlStateManager.shaderSource(VERTEX_SHADER, getShaderSource(VERTEX_VERT));
        GlStateManager.compileShader(VERTEX_SHADER);
    }

    public Shader(String fragmentShaderName) {
        int programId = GlStateManager.createProgram();

        try {
            int fragmentShader = GlStateManager.createShader(GL30.GL_FRAGMENT_SHADER);
            GlStateManager.shaderSource(fragmentShader, getShaderSource(fragmentShaderName));
            GlStateManager.compileShader(fragmentShader);

            int isFragmentCompiled = GL30.glGetShaderi(fragmentShader, GL30.GL_COMPILE_STATUS);
            if(isFragmentCompiled == 0) {
                GlStateManager.deleteShader(fragmentShader);
                System.err.println("Fragment shader couldn't compile. It has been deleted.");
            }

            GlStateManager.attachShader(programId, VERTEX_SHADER);
            GlStateManager.attachShader(programId, fragmentShader);
            GlStateManager.linkProgram(programId);
        } catch (Exception ignored) {
        }

        this.programId = programId;
    }

    public void load() {
        GlStateManager.useProgram(programId);
    }

    public void unload() {
        GlStateManager.useProgram(0);
    }

    public int getUniform(String name) {
        return GL30.glGetUniformLocation(programId, name);
    }

    public void setUniformf(String name, float... args) {
        int loc = GL30.glGetUniformLocation(programId, name);
        switch (args.length) {
            case 1:
                GL30.glUniform1f(loc, args[0]);
                break;
            case 2:
                GL30.glUniform2f(loc, args[0], args[1]);
                break;
            case 3:
                GL30.glUniform3f(loc, args[0], args[1], args[2]);
                break;
            case 4:
                GL30.glUniform4f(loc, args[0], args[1], args[2], args[3]);
                break;
        }
    }

    public void setUniformi(String name, int... args) {
        int loc = GL30.glGetUniformLocation(programId, name);
        switch (args.length) {
            case 1:
                GL30.glUniform1i(loc, args[0]);
                break;
            case 2:
                GL30.glUniform2i(loc, args[0], args[1]);
                break;
            case 3:
                GL30.glUniform3i(loc, args[0], args[1], args[2]);
                break;
            case 4:
                GL30.glUniform4i(loc, args[0], args[1], args[2], args[3]);
                break;
        }
    }

    public void setUniformfb(String name, FloatBuffer buffer) {
        GL30.glUniform1fv(GL30.glGetUniformLocation(programId, name), buffer);
    }

    public static void draw() {
        draw(0, 0, mc.getMainWindow().getScaledWidth(), mc.getMainWindow().getScaledHeight());
    }

    public static void draw(double x, double y, double width, double height) {
        GL11.glBegin(GL_QUADS);
        GL11.glTexCoord2d(0, 0);
        GL11.glVertex2d(x, y);
        GL11.glTexCoord2d(0, 1);
        GL11.glVertex2d(x, y + height);
        GL11.glTexCoord2d(1, 1);
        GL11.glVertex2d(x + width, y + height);
        GL11.glTexCoord2d(1, 0);
        GL11.glVertex2d(x + width, y);
        GL11.glEnd();
    }

    public static String getShaderSource(String link) {
        String source = "";

        try {
            LineNumberReader reader = new LineNumberReader(new InputStreamReader(new URL(link).openStream()));
            source = reader.lines().filter(str -> !str.isEmpty()).map(str -> str.replace("\t", "")).collect(Collectors.joining("\n"));
            reader.close();
        } catch (IOException ignored) {
        }

        return source;
    }
}
