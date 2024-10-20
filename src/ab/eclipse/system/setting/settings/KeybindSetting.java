package ab.eclipse.system.setting.settings;


import ab.eclipse.impl.function.Function;
import ab.eclipse.system.setting.IVisible;
import ab.eclipse.system.setting.Setting;
import ab.eclipse.system.setting.SettingManager;
import ab.eclipse.system.setting.SettingType;

public class KeybindSetting extends Setting<Integer> {
    public KeybindSetting(Function function) {
        super(function, SettingType.Keybind);
    }

    public boolean checkKey(int a, boolean keyboard) {
        return getValue() - (keyboard ? 0 : 90000) == a;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public KeybindSetting setValue(Integer value) {
        this.value = value;

        if (initValue == null) {
            initValue = value;
        }

        return this;
    }

    @Override
    public Integer getInitValue() {
        return initValue;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public KeybindSetting setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public KeybindSetting setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public boolean isVisible() {
        return visible.visible();
    }

    @Override
    public KeybindSetting setVisible(IVisible visible) {
        this.visible = visible;
        return this;
    }

    @Override
    public KeybindSetting build() {
        SettingManager.add(this);
        return this;
    }

    @Override
    public void unpack(String in) {
        if (in.split(":")[0].equalsIgnoreCase(getName())) {
            setValue(Integer.parseInt(in.split(":")[1]));
        }
    }

    @Override
    public String toCfg() {
        return getName().concat(":").concat(getValue() + "");
    }
}
