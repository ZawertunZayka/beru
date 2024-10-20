package ab.eclipse.gui.buttons;


import ab.eclipse.gui.GuiImpl;
import ab.eclipse.gui.SettingButton;
import ab.eclipse.system.setting.Setting;
import ab.eclipse.utils.font.main.IFont;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class BooleanSetting extends SettingButton {
    public BooleanSetting(Setting<Boolean> setting, double x, double y, double w, double h, double addH) {
        super(setting, x, y, w, h, addH);
    }

    @Override
    public void render(int mx, int my) {
        IFont.drawCenteredY(IFont.MONTSERRAT_MEDIUM, setting.getName(), (float) (x + 7), (float) (y + h / 2), Color.WHITE, 7);
        drawValue();
    }

    public void drawValue() {

    }

    @Override
    public void mouse(double mx, double my, int button, int action) {
        if (GuiImpl.isHover(x, y, w, h, mx, my) && button == GLFW.GLFW_MOUSE_BUTTON_1) {
            ((Setting<Boolean>) setting).setValue(!((Setting<Boolean>) setting).getValue());
        }
    }

    @Override
    public void key(int key, int action) {

    }

    @Override
    public void close() {

    }
}
