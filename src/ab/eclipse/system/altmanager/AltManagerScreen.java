package ab.eclipse.system.altmanager;

import ab.eclipse.autobuy.PrintStack;
import ab.eclipse.utils.AuthUtils;
import ab.eclipse.utils.ScissorUtils;
import ab.eclipse.utils.animation.AnimationUtils;
import ab.eclipse.utils.font.main.IFont;
import ab.eclipse.utils.render.Render2d;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Session;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import static ab.eclipse.EclipseFuntime.mc;
import static ab.eclipse.gui.GuiImpl.isHover;


public class AltManagerScreen extends Screen {
    public double x, y, w = 200, h = 200;
    public ConcurrentLinkedQueue<AltManagerButton> altManagerButtonList = new ConcurrentLinkedQueue<>();

    public String search = "";
    public boolean write;
    public double scroll = 0, targetScroll;
    public int dot = 0;

    public AltManagerScreen() {
        super(ITextComponent.getTextComponentOrEmpty("alt"));

        x = (double) mc.getMainWindow().getScaledWidth() / 2 - w / 2;
        y = (double) mc.getMainWindow().getScaledHeight() / 2 - h / 2;

        double y2 = y + 26;
        for (User user : AltManager.getAccount()) {
            altManagerButtonList.add(new AltManagerButton(x, y2, w, 18, user));
            y2 += 20;
        }

    }


    public double calcH() {
        return altManagerButtonList.stream().filter(f -> f.user.name.toLowerCase().contains(search.toLowerCase())).toList().size() * 20 + 4;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
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
        String reset = "добавить";
        Render2d.drawRoundedRect(x + w - 4 - IFont.getWidth(IFont.MONTSERRAT_MEDIUM, reset, 8) - 6, y + 4 + 10 - IFont.getHeight(IFont.MONTSERRAT_MEDIUM, reset, 8) / 2 - 1, IFont.getWidth(IFont.MONTSERRAT_MEDIUM, reset, 8) + 4, IFont.getHeight(IFont.MONTSERRAT_MEDIUM, reset, 8) + 2, 2, new Color(43, 44, 44, 255));
        IFont.drawCenteredXY(IFont.MONTSERRAT_MEDIUM, reset, (float) (x + w - 4 - IFont.getWidth(IFont.MONTSERRAT_MEDIUM, reset, 8) / 2 - 4), (float) (y + 14), Color.WHITE, 8);
        GL11.glPopMatrix();

        double y2 = y + 26 + scroll;
        ScissorUtils.enableScissor(x, y + 24, x + w, y + h);
        for (AltManagerButton autoBuyButton : altManagerButtonList) {
            if (!autoBuyButton.user.name.toLowerCase().contains(search.toLowerCase())) continue;
            autoBuyButton.y = y2;
            y2 += 20;
            if (autoBuyButton.y < y || autoBuyButton.y > y + h) continue;
            autoBuyButton.render(mouseX, mouseY);
        }
        ScissorUtils.disableScissor();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isHover(x, y + 24, w, h - 24, mouseX, mouseY)) {
            for (AltManagerButton altManagerButton : altManagerButtonList) {
                if (!altManagerButton.user.name.toLowerCase().contains(search.toLowerCase())) continue;
                altManagerButton.click((int) mouseX, (int) mouseY, button);
            }
        }
        if (isHover(x, y + 4, w, 20, mouseX, mouseY)) {
            PrintStack.setCallback(this.getClass(), () -> write = false);
            write = true;
        }
        if (isHover(x + w - 8 - IFont.getWidth(IFont.MONTSERRAT_MEDIUM, "добавить", 8) - 6, y + 4, IFont.getWidth(IFont.MONTSERRAT_MEDIUM, "добавить", 8) + 8, 20, mouseX, mouseY) && button == GLFW.GLFW_MOUSE_BUTTON_1) {
            if (!search.isEmpty()) {
                User a = new User(search);
                AltManager.add(a);
                AuthUtils.login(a);
                altManagerButtonList.clear();
                double y2 = y + 26;
                for (User user : AltManager.getAccount()) {
                    altManagerButtonList.add(new AltManagerButton(x, y2, w, 18, user));
                    y2 += 20;
                }
            }
            write = false;
            search = "";
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }


    private boolean check(char in) {
        for (char a = 'a'; a <= 'z'; a++) {
            if (in == a || in == Character.toUpperCase(a)) return true;
        }
        for (char b = '0'; b <= '9'; b++) {
            if (in == b) return true;
        }
        return in == '_';
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (write) {
            if (keyCode == GLFW.GLFW_KEY_V && hasControlDown() && !mc.keyboardListener.getClipboardString().isEmpty()) {
                String s = mc.keyboardListener.getClipboardString();
                boolean d = true;
                for (char c : s.toCharArray()) {
                    if (!check(c)) {
                        d = false;
                        break;
                    }
                }
                if (d) {
                    search += mc.keyboardListener.getClipboardString();
                }
            }
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
        if (write && check(codePoint)) {
            search += codePoint;
        }
        return super.charTyped(codePoint, modifiers);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
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
    public void onClose() {
        AltManager.save();
    }
}