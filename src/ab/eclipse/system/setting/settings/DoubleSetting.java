package ab.eclipse.system.setting.settings;

import ab.eclipse.impl.function.Function;
import ab.eclipse.system.setting.IVisible;
import ab.eclipse.system.setting.Setting;
import ab.eclipse.system.setting.SettingManager;
import ab.eclipse.system.setting.SettingType;
import lombok.Getter;


@Getter
public class DoubleSetting extends Setting<Double> {
    private double min, max;

    public DoubleSetting(Function function) {
        super(function, SettingType.Double);
    }

    public DoubleSetting setMax(double max) {
        this.max = max;
        return this;
    }

    public DoubleSetting setMin(double min) {
        this.min = min;
        return this;
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public DoubleSetting setValue(Double value) {
        this.value = value;

        if (initValue == null) {
            initValue = value;
        }

        return this;
    }

    @Override
    public Double getInitValue() {
        return initValue;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DoubleSetting setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public DoubleSetting setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public boolean isVisible() {
        return visible.visible();
    }

    @Override
    public DoubleSetting setVisible(IVisible visible) {
        this.visible = visible;
        return this;
    }

    @Override
    public DoubleSetting build() {
        SettingManager.add(this);
        return this;
    }

    @Override
    public void unpack(String in) {
        if (in.split(":")[0].equalsIgnoreCase(getName())) {
            setValue(Double.parseDouble(in.split(":")[1]));
        }
    }

    @Override
    public String toCfg() {
        return getName().concat(":").concat(getValue() + "");
    }
}

