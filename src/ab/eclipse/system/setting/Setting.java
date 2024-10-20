package ab.eclipse.system.setting;


import ab.eclipse.impl.function.Function;

public abstract class Setting<T> implements ISetting<T>, ICfg {
    public T value, initValue;
    public String name, description;
    public Function function;
    public IVisible visible = () -> true;
    public SettingType type;

    public Setting(Function function, SettingType type) {
        this.function = function;
        this.type = type;
    }
}