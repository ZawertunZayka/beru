package ab.eclipse.system.setting.settings;


import ab.eclipse.impl.function.Function;
import ab.eclipse.system.setting.IVisible;
import ab.eclipse.system.setting.Setting;
import ab.eclipse.system.setting.SettingManager;
import ab.eclipse.system.setting.SettingType;

public class BooleanSetting extends Setting<Boolean> {
    public BooleanSetting(Function function) {
        super(function, SettingType.Boolean);
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public BooleanSetting setValue(Boolean value) {
        this.value = value;

        if (initValue == null) {
            initValue = value;
        }

        return this;
    }

    @Override
    public Boolean getInitValue() {
        return initValue;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BooleanSetting setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public BooleanSetting setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public boolean isVisible() {
        return visible.visible();
    }

    @Override
    public BooleanSetting setVisible(IVisible visible) {
        this.visible = visible;
        return this;
    }

    @Override
    public BooleanSetting build() {
        SettingManager.add(this);
        return this;
    }

    @Override
    public void unpack(String in) {
        if (in.split(":")[0].equalsIgnoreCase(getName())) {
            setValue(Boolean.parseBoolean(in.split(":")[1]));
        }
    }

    @Override
    public String toCfg() {
        return getName().concat(":").concat(getValue() + "");
    }
}
