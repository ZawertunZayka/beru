package ab.eclipse.system.setting.settings;

import ab.eclipse.impl.function.Function;
import ab.eclipse.system.setting.IVisible;
import ab.eclipse.system.setting.Setting;
import ab.eclipse.system.setting.SettingManager;
import ab.eclipse.system.setting.SettingType;
import lombok.Getter;


@Getter
public class IntegerSetting extends Setting<Integer> {
    private int min, max;

    public IntegerSetting(Function function) {
        super(function, SettingType.Integer);
    }

    public IntegerSetting setMax(int max) {
        this.max = max;
        return this;
    }

    public IntegerSetting setMin(int min) {
        this.min = min;
        return this;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public IntegerSetting setValue(Integer value) {
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
    public IntegerSetting setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public IntegerSetting setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public boolean isVisible() {
        return visible.visible();
    }

    @Override
    public IntegerSetting setVisible(IVisible visible) {
        this.visible = visible;
        return this;
    }

    @Override
    public IntegerSetting build() {
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
