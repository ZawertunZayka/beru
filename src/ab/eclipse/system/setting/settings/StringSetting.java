package ab.eclipse.system.setting.settings;


import ab.eclipse.impl.function.Function;
import ab.eclipse.system.setting.IVisible;
import ab.eclipse.system.setting.Setting;
import ab.eclipse.system.setting.SettingManager;
import ab.eclipse.system.setting.SettingType;

public class StringSetting extends Setting<String> {
    public StringSetting(Function function) {
        super(function, SettingType.String);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public StringSetting setValue(String value) {
        this.value = value;

        if (initValue == null) {
            initValue = value;
        }

        return this;
    }

    @Override
    public String getInitValue() {
        return initValue;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public StringSetting setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public StringSetting setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public boolean isVisible() {
        return visible.visible();
    }

    @Override
    public StringSetting setVisible(IVisible visible) {
        this.visible = visible;
        return this;
    }

    @Override
    public StringSetting build() {
        SettingManager.add(this);
        return this;
    }

    @Override
    public void unpack(String in) {
        if (in.split(":")[0].equalsIgnoreCase(getName())) {
            setValue(in.split(":")[1]);
        }
    }

    @Override
    public String toCfg() {
        return getName().concat(":").concat(getValue());
    }
}