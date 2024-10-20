package ab.eclipse.gui;


import ab.eclipse.gui.buttons.BooleanSetting;
import ab.eclipse.impl.function.Function;
import ab.eclipse.system.setting.Setting;
import ab.eclipse.system.setting.SettingManager;
import ab.eclipse.utils.animation.AnimationUtils;
import ab.eclipse.utils.animation.ColorTransfusion;
import ab.eclipse.utils.font.main.IFont;
import ab.eclipse.utils.render.Render2d;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FunctionButton implements GuiImpl {
    public Function function;
    public double x, y, w, h, animH, initH;
    public boolean open;

    private final ColorTransfusion textColor;

    private final List<SettingButton> buttonList = new ArrayList<>();

    public FunctionButton(Function function, double x, double y, double w, double h) {
        this.function = function;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.initH = h;
        this.animH = h;

        if (function.isToggled()) {
            textColor = new ColorTransfusion(Color.WHITE);
        } else {
            textColor = new ColorTransfusion(Color.GRAY);
        }

        double sy = y + initH;
        for (Setting<?> setting : SettingManager.getSettingList(function)) {
            SettingButton addS = null;
            double addV = 0;

            switch (setting.type) {
                case Boolean -> {
                    addV = h + 2;
                    addS = new BooleanSetting((Setting<Boolean>) setting, x, sy, w, h, addV);
                }
            }

            if (addS == null) continue;
            buttonList.add(addS);
            sy += addV;
        }
    }

    public double getH() {
        double f = 0;
        for (SettingButton settingButton : buttonList) {
            f += settingButton.addH;
        }
        return h + f + 2;
    }

    @Override
    public void render(int mx, int my) {
        h = AnimationUtils.fast(h, animH, 10);

        textColor.animate(function.isToggled() ? Color.WHITE : Color.GRAY, 10);

        Render2d.drawRoundedRect(x + 4, y, w - 8, h, 4, new Color(19, 19, 19, 255));
        IFont.drawCenteredY(IFont.MONTSERRAT_MEDIUM, function.getName(), (float) (x + 7), (float) (y + initH / 2), textColor.getColor(255), 8);

        double sy = y + initH;
        for (SettingButton settingButton : buttonList) {
            settingButton.x = x;
            settingButton.y = sy;
            settingButton.render(mx, my);
            sy += settingButton.addH;
        }
    }

    @Override
    public void mouse(double mx, double my, int button, int action) {
        if (action == GLFW.GLFW_PRESS) {
            if (GuiImpl.isHover(x, y, w, initH, mx, my)) {
                if (button == GLFW.GLFW_MOUSE_BUTTON_1) {
                    function.toggled();
                }
                if (button == GLFW.GLFW_MOUSE_BUTTON_2 && !buttonList.isEmpty()) {
                    open = !open;
                    if (open) {
                        animH = getH();
                    } else {
                        animH = initH;
                    }
                }
            }
        }
    }

    @Override
    public void key(int key, int action) {

    }

    @Override
    public void close() {
    }
}
