package ab.eclipse.system.setting.settings;



import ab.eclipse.impl.function.Function;
import ab.eclipse.system.setting.IVisible;
import ab.eclipse.system.setting.Setting;
import ab.eclipse.system.setting.SettingManager;
import ab.eclipse.system.setting.SettingType;


import java.util.List;

public class ListSetting extends Setting<String> {
    private List<String> valueList;

    public ListSetting(Function function) {
        super(function, SettingType.List);
    }

    public ListSetting setList(List<String> values) {
        this.valueList = values;
        return this;
    }

    public List<String> getList() {
        return valueList;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public ListSetting setValue(String value) {
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
    public ListSetting setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public ListSetting setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public boolean isVisible() {
        return visible.visible();
    }

    @Override
    public ListSetting setVisible(IVisible visible) {
        this.visible = visible;
        return this;
    }

    @Override
    public ListSetting build() {
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