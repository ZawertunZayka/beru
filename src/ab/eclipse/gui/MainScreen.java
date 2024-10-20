package ab.eclipse.gui;

import ab.eclipse.impl.function.Category;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static ab.eclipse.EclipseFuntime.mc;


public class MainScreen extends Screen {
    private List<Window> windows = new ArrayList<>();
    
    public MainScreen() {
        super(ITextComponent.getTextComponentOrEmpty("luitznefar"));

        double cx = mc.getMainWindow().getScaledWidth() / 2 - 210;
        double cy = mc.getMainWindow().getScaledHeight() / 2 - 200;

        for (Category value : Category.values()) {
            windows.add(new Window(cx, cy, 100, 16, value));
            cx += 105;
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        for (Window window : windows) {
            window.render(mouseX, mouseY);
        }
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for (Window window : windows) {
            window.mouse(mouseX, mouseY, button, GLFW.GLFW_RELEASE);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            mc.displayGuiScreen(null);
        } else {
            for (Window window : windows) {
                window.key(keyCode, GLFW.GLFW_PRESS);
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        for (Window window : windows) {
            window.key(keyCode, GLFW.GLFW_RELEASE);
        }
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (Window window : windows) {
            window.mouse(mouseX, mouseY, button, GLFW.GLFW_PRESS);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        for (Window window : windows) {
            window.dragged(mouseX, mouseY, button, dragX, dragY);
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public void closeScreen() {
        for (Window window : windows) {
            window.close();
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        for (Window window : windows) {
            if (delta > 0)
                window.nextY -= (hasControlDown() ? 30 : 10);
            if (delta < 0)
                window.nextY += (hasControlDown() ? 30 : 10);
        }
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
