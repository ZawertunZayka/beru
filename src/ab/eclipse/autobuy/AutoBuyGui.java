package ab.eclipse.autobuy;

import ab.eclipse.EclipseFuntime;
import ab.eclipse.autobuy.manager.AutoBuyItem;
import ab.eclipse.autobuy.manager.AutoBuyManager;
import ab.eclipse.autobuy.manager.CustomAutoBuyItem;
import ab.eclipse.autobuy.manager.IgnoreManager;
import ab.eclipse.utils.ScissorUtils;
import ab.eclipse.utils.animation.AnimationUtils;
import ab.eclipse.utils.font.main.IFont;
import ab.eclipse.utils.render.Render2d;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import java.util.concurrent.ConcurrentLinkedQueue;

import static ab.eclipse.EclipseFuntime.mc;


public class AutoBuyGui extends Screen {
    public double x, y, w = 200, h = 200;
    public ConcurrentLinkedQueue<AutoBuyButton> autoBuyButtons = new ConcurrentLinkedQueue<>();

    public SelectWindow selectWindow;
    public HistoryScreen historyScreen;
    public AutoPayWindow autoPayWindow;
    public FunctionWindow functionWindow;

    public String search = "";
    public boolean write;
    public double scroll = 0, targetScroll;
    public int dot = 0;

    public AutoBuyGui() {
        super(ITextComponent.getTextComponentOrEmpty("ab"));

        x = (double) mc.getMainWindow().getScaledWidth() / 2 - w / 2;
        y = (double) mc.getMainWindow().getScaledHeight() / 2 - h / 2;

        selectWindow = new SelectWindow(x + 210, y, w, h);
        historyScreen = new HistoryScreen(x - 210, y, w, h, this);
        autoPayWindow = new AutoPayWindow(x - 50, y + h + 10, 100, 18);
        functionWindow = new FunctionWindow(x, y - 10 - (EclipseFuntime.functionList.size() * 20 + 8), w, EclipseFuntime.functionList.size() * 20 + 6);

        double y2 = y + 26;
        for (AutoBuyItem item : AutoBuyManager.getItems()) {
            autoBuyButtons.add(new AutoBuyButton(x, y2, w, 18, item, item instanceof
                    CustomAutoBuyItem ? ((CustomAutoBuyItem) item).name : item.item.getName().getString()));
            y2 += 20;
        }
    }

    public void open() {
        historyScreen.open();
        selectWindow.open();
    }

    public void addItem(AutoBuyItem item) {
        double y2 = y + 26;
        for (AutoBuyButton a : autoBuyButtons) {
            y2 += 20;
        }
        autoBuyButtons.add(new AutoBuyButton(x, y2, w, 18, item, item instanceof CustomAutoBuyItem ? ((CustomAutoBuyItem) item).name : item.item.getName().getString()));
    }

    public double calcH() {
        return autoBuyButtons.stream().filter(f -> f.name.toLowerCase().contains(search.toLowerCase())).toList().size() * 20 + 4;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        targetScroll = MathHelper.clamp(targetScroll, -(calcH() - (h - 24)), 0);
        if (calcH() - (h - 24) < 0) {
            targetScroll = 0;
        }
        scroll = AnimationUtils.fast(scroll, targetScroll);

        Render2d.drawRoundedRect(x, y, w, h, 4, new Color(39, 37, 37, 255));

        if (dot > 20) dot = 0;
        if (write) dot++;

        GL11.glPushMatrix();
        GL11.glTranslatef(0, 0, 300);
        Render2d.drawRoundedRect(x + 4, y + 4, w - 8, 20, 2, new Color(16, 15, 15, 255));
        IFont.drawCenteredY(IFont.MONTSERRAT_MEDIUM, search.isEmpty() && !write ? "введите текст..." : search + (write ? dot > 10 ? "_" : "" : ""), (float) (x + 8), (float) (y + 14), search.isEmpty() ? Color.GRAY : Color.WHITE, 8);
        String reset = "очистить";
        Render2d.drawRoundedRect(x + w - 4 - IFont.getWidth(IFont.MONTSERRAT_MEDIUM, reset, 8) - 6, y + 4 + 10 - IFont.getHeight(IFont.MONTSERRAT_MEDIUM, reset, 8) / 2 - 1, IFont.getWidth(IFont.MONTSERRAT_MEDIUM, reset, 8) + 4, IFont.getHeight(IFont.MONTSERRAT_MEDIUM, reset, 8) + 2, 2, new Color(43, 44, 44, 255));
        IFont.drawCenteredXY(IFont.MONTSERRAT_MEDIUM, reset, (float) (x + w - 4 - IFont.getWidth(IFont.MONTSERRAT_MEDIUM, reset, 8) / 2 - 4), (float) (y + 14), Color.WHITE, 8);
        GL11.glPopMatrix();

        double y2 = y + 26 + scroll;
        ScissorUtils.enableScissor(x, y + 24, x + w, y + h);
        for (AutoBuyButton autoBuyButton : autoBuyButtons) {
            if (!autoBuyButton.name.toLowerCase().contains(search.toLowerCase())) continue;
            autoBuyButton.y = y2;
            y2 += 20;
            if (autoBuyButton.y < y || autoBuyButton.y > y + h) continue;
            autoBuyButton.render(mouseX, mouseY);
        }
        ScissorUtils.disableScissor();

        selectWindow.render(mouseX, mouseY);
        historyScreen.render(mouseX, mouseY);
        autoPayWindow.render();
        functionWindow.render(mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        selectWindow.click(mouseX, mouseY, button);
        autoPayWindow.click(mouseX, mouseY, button);
        functionWindow.click(mouseX, mouseY, button);
        if (isHover(x, y + 24, w, h - 24, mouseX, mouseY)) {
            for (AutoBuyButton autoBuyButton : autoBuyButtons) {
                if (!autoBuyButton.name.toLowerCase().contains(search.toLowerCase())) continue;
                autoBuyButton.click((int) mouseX, (int) mouseY, button);
            }
        }
        if (isHover(x, y + 4, w, 20, mouseX, mouseY)) {
            PrintStack.setCallback(this.getClass(), () -> write = false);
            write = true;
        }
        if (isHover(x + w - 8 - IFont.getWidth(IFont.MONTSERRAT_MEDIUM, "очистить", 8) - 6, y + 4, IFont.getWidth(IFont.MONTSERRAT_MEDIUM, "очистить", 8) + 8, 20, mouseX, mouseY) && button == GLFW.GLFW_MOUSE_BUTTON_1) {
            write = false;
            search = "";
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        selectWindow.key(keyCode);
        autoPayWindow.key(keyCode);
        functionWindow.key(keyCode);
        for (AutoBuyButton autoBuyButton : autoBuyButtons) {
            if (!autoBuyButton.name.toLowerCase().contains(search.toLowerCase())) continue;
            autoBuyButton.key(keyCode);
        }
        if (write) {
            if (keyCode == GLFW.GLFW_KEY_ENTER) {
                write = false;
            }
            if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
                search = getStringIgnoreLastChar(search);
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public static String getStringIgnoreLastChar(String str) {
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < str.toCharArray().length - 1; i++) {
            s.append(str.toCharArray()[i]);
        }

        return s.toString();
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        selectWindow.charTyped(codePoint);
        autoPayWindow.charTyped(codePoint);
        for (AutoBuyButton autoBuyButton : autoBuyButtons) {
            if (!autoBuyButton.name.toLowerCase().contains(search.toLowerCase())) continue;
            autoBuyButton.charTyped(codePoint);
        }
        if (write) {
            search += codePoint;
        }
        return super.charTyped(codePoint, modifiers);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        historyScreen.scroll(mouseX, mouseY, delta);
        selectWindow.scroll(mouseX, mouseY, delta);
        if (isHover(x, y + 24, w, h - 24, mouseX, mouseY) && (calcH() - (h - 24) > 0)) {
            if (delta < 0) {
                if (targetScroll > -(calcH() - (h - 24))) {
                    targetScroll -= 15;
                }
                targetScroll = MathHelper.clamp(targetScroll, -(calcH() - (h - 24)), 0);
            }
            if (delta > 0) {
                if (targetScroll < 0) {
                    targetScroll += 15;
                }
                targetScroll = MathHelper.clamp(targetScroll, -(calcH() - (h - 24)), 0);
            }
        }
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        AutoBuyManager.save();
        IgnoreManager.save();
        EclipseFuntime.save();
    }

    public static boolean isHover(double X, double Y, double W, double H, double mX, double mY) {
        return mX >= X&& mX <= (X + W)  && mY >= Y  && mY <= (Y + H) ;
    }
}
