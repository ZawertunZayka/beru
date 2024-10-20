package ab.eclipse.system.altmanager;


import ab.eclipse.autobuy.AutoBuyGui;
import ab.eclipse.utils.AuthUtils;
import ab.eclipse.utils.font.main.IFont;
import ab.eclipse.utils.render.Render2d;
import net.minecraft.client.gui.screen.MainMenuScreen;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

import static ab.eclipse.EclipseFuntime.mc;


public class AltManagerButton {
    public double x, y, w, h;
    public User user;

    public AltManagerButton(double x, double y, double w, double h, User user) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.user = user;
    }

    public void render(int mx, int my) {
        Render2d.drawRoundedRect(x + 4, y, w - 8, h, 2, new Color(16, 15, 15, 255));
        IFont.drawCenteredY(IFont.MONTSERRAT_MEDIUM, user.name, (float) (x + 8), (float) (y + h / 2), mc.getSession().getUsername().equals(user.name) ? Color.WHITE : Color.GRAY, 8);
        IFont.drawCenteredY(IFont.MONTSERRAT_MEDIUM, " - ", (float) (x + w - 8 - IFont.getWidth(IFont.MONTSERRAT_MEDIUM, " - ", 8)), (float) (y + h / 2), Color.RED, 8);
    }

    public void click(double mx, double my, int b) {
        if (AutoBuyGui.isHover(x, y, w - 32, h, mx, my) && b == GLFW.GLFW_MOUSE_BUTTON_1) {
            AuthUtils.login(user);
        }
        if (AutoBuyGui.isHover(x + w - 32, y, 32, h, mx, my) && b == GLFW.GLFW_MOUSE_BUTTON_1) {
            MainMenuScreen.altManagerScreen.altManagerButtonList.remove(this);
            AltManager.remove(user);
        }
    }
}
