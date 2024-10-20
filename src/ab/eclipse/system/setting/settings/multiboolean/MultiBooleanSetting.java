package ab.eclipse.system.setting.settings.multiboolean;



import ab.eclipse.impl.function.Function;
import ab.eclipse.system.setting.IVisible;
import ab.eclipse.system.setting.Setting;
import ab.eclipse.system.setting.SettingManager;
import ab.eclipse.system.setting.SettingType;

import java.util.List;

public class MultiBooleanSetting extends Setting<List<MultiBooleanValue>> {
    public MultiBooleanSetting(Function function) {
        super(function, SettingType.MultiBoolean);
    }

    public boolean get(String name) {
        try {
            return getValue().stream().filter(multiBooleanSetting -> multiBooleanSetting.getName().equals(name)).toList().get(0).isValue();
        } catch (Exception exception) {
            return getValue().get(0).isValue();
        }
    }

    public boolean get(int index) {
        try {
            return getValue().get(index).isValue();
        } catch (Exception exception) {
            return getValue().get(0).isValue();
        }
    }

    public MultiBooleanValue getMultiBooleanValue(String name) {
        try {
            return getValue().stream().filter(multiBooleanSetting -> multiBooleanSetting.getName().equals(name)).toList().get(0);
        } catch (Exception exception) {
            return getValue().get(0);
        }
    }

    public MultiBooleanValue getMultiBooleanValue(int index) {
        try {
            return getValue().get(index);
        } catch (Exception exception) {
            return getValue().get(0);
        }
    }

    public String getCount() {
        String size = String.valueOf(value.size());
        int i = 0;

        for (MultiBooleanValue multiBooleanValue : value) {
            if (multiBooleanValue.isValue())
                i++;
        }

        return i + "/" + size;
    }

    @Override
    public List<MultiBooleanValue> getValue() {
        return value;
    }

    @Override
    public MultiBooleanSetting setValue(List<MultiBooleanValue> value) {
        this.value = value;

        if (initValue == null) {
            initValue = value;
        }

        return this;
    }

    @Override
    public List<MultiBooleanValue> getInitValue() {
        return initValue;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public MultiBooleanSetting setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public MultiBooleanSetting setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public boolean isVisible() {
        return visible.visible();
    }

    @Override
    public MultiBooleanSetting setVisible(IVisible visible) {
        this.visible = visible;
        return this;
    }

    @Override
    public MultiBooleanSetting build() {
        SettingManager.add(this);
        return this;
    }

    @Override
    public void unpack(String in) {

    }

    @Override
    public String toCfg() {
        StringBuilder stringBuilder = new StringBuilder();

        for (MultiBooleanValue multiBooleanValue : getValue()) {
            stringBuilder.append(multiBooleanValue.getName()).append(",").append(multiBooleanValue.isValue()).append("|");
        }

        return getName().concat(":").concat(stringBuilder.toString());
    }
}
