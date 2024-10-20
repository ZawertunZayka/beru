package ab.eclipse.gui;


import ab.eclipse.impl.function.Category;
import ab.eclipse.impl.function.Function;
import ab.eclipse.impl.function.FunctionManager;
import ab.eclipse.utils.animation.AnimationUtils;
import ab.eclipse.utils.font.main.IFont;
import ab.eclipse.utils.render.Render2d;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Window implements GuiImpl {
    public double x, y, nextY, w, h;
    public Category category;
    private double dragX;
    private double dragY;
    private double rotate, nextRotate;
    private boolean dragging;
    private List<FunctionButton> buttonList = new ArrayList<>();

    public Window(double x, double y, double w, double h, Category category) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.nextY = y;
        this.category = category;

        double yp = y + h + 6;
        for (Function function : FunctionManager.getFunctionList(category)) {
            buttonList.add(new FunctionButton(function, x, yp, w, h + 2));
            yp += h + 4;
        }
    }

    public double totalH() {
        double fh = 0;
        for (FunctionButton functionButton : buttonList) {
            fh += functionButton.h + 2;
        }
        return h + 8 + fh;
    }

    @Override
    public void render(int mx, int my) {
        y = AnimationUtils.fast(y, nextY);

        if (dragging) {
            x = dragX + mx;
            y = dragY + my;
            nextY = y;
        }

        GL11.glPushMatrix();

        rotate = AnimationUtils.fast(rotate, nextRotate);
        GL11.glTranslatef((float) (x + w / 2), (float) (y + h / 2), 0);
        GL11.glRotated(rotate, 0, 0, 1);
        GL11.glTranslatef((float) -(x + w / 2), -(float) (y + h / 2), 0);

        Render2d.drawRoundedRect(x, y, w, totalH(), 6, new Color(53, 53, 53, 255));
        IFont.drawCenteredXY(IFont.MONTSERRAT_MEDIUM, category.name(), (float) (x + w / 2), (float) (y + (h + 4) / 2), Color.WHITE, 10);

        double yp = y + h + 6;
        for (FunctionButton functionButton : buttonList) {
            functionButton.x = x;
            functionButton.y = yp;
            functionButton.render(mx, my);
            yp += functionButton.h + 2;
        }

        GL11.glPopMatrix();
        nextRotate = 0;
    }

    public void dragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (GuiImpl.isHover(x, y, w, h, mouseX, mouseY) && dragging) {
            if (dragX != 0) {
                nextRotate = (dragX < 0 ? -15 : 15);
            }
        }
    }

    @Override
    public void mouse(double mx, double my, int button, int action) {
        if (action == GLFW.GLFW_PRESS) {
            if (GuiImpl.isHover(x, y, w, h, mx, my)) {
                if (button == 0) {
                    dragging = true;
                    dragX = x - mx;
                    dragY = y - my;
                }
            }
        }

        if (action == GLFW.GLFW_RELEASE) {
            dragging = false;
        }

        for (FunctionButton functionButton : buttonList) {
            functionButton.mouse(mx, my, button, action);
        }
    }

    @Override
    public void key(int key, int action) {

    }

    @Override
    public void close() {
        rotate = 0;
        nextRotate = 0;
    }
}
