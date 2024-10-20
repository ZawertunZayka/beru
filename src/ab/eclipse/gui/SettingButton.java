package ab.eclipse.gui;


import ab.eclipse.system.setting.Setting;
import lombok.Getter;

//@Getter
public abstract class SettingButton implements GuiImpl {
    public Setting<?> setting;

    public double x, y, w, h, initH, addH;

    public SettingButton(Setting<?> setting, double x, double y, double w, double h, double addH) {
        this.setting = setting;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.initH = h;
        this.addH = addH;
    }
}