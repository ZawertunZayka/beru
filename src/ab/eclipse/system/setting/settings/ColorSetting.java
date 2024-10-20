package ab.eclipse.system.setting.settings;



import ab.eclipse.impl.function.Function;
import ab.eclipse.system.setting.IVisible;
import ab.eclipse.system.setting.Setting;
import ab.eclipse.system.setting.SettingManager;
import ab.eclipse.system.setting.SettingType;

import java.awt.*;

public class ColorSetting extends Setting<Color> {
    public ColorSetting(Function function) {
        super(function, SettingType.Color);
    }

    @Override
    public Color getValue() {
        return value;
    }

    @Override
    public ColorSetting setValue(Color value) {
        this.value = value;

        if (initValue == null) {
            initValue = value;
        }

        return this;
    }

    @Override
    public Color getInitValue() {
        return initValue;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ColorSetting setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public ColorSetting setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public boolean isVisible() {
        return visible.visible();
    }

    @Override
    public ColorSetting setVisible(IVisible visible) {
        this.visible = visible;
        return this;
    }

    @Override
    public ColorSetting build() {
        SettingManager.add(this);
        return this;
    }

    @Override
    public void unpack(String in) {
        if (in.split(":")[0].equalsIgnoreCase(getName())) {
            Color unpack = new Color(Integer.parseInt(in.split(":")[1].split(";")[0]));
            setValue(new Color(unpack.getRed(), unpack.getGreen(), unpack.getBlue(), Integer.parseInt(in.split(":")[1].split(";")[1])));
        }
    }

    @Override
    public String toCfg() {
        return getName().concat(":").concat(getValue().getRGB() + ";" + getValue().getAlpha());
    }
}


