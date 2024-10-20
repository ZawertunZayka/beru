package ab.eclipse.system.setting;

import ab.eclipse.impl.function.Function;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SettingManager {
    @Getter
    private static final List<Setting<?>> settingList = new ArrayList<>();

    public static void add(Setting<?> setting) {
        settingList.add(setting);
    }

    public static List<Setting<?>> getSettingList(SettingType settingType) {
        return getSettingList().stream().filter(setting -> setting.type.equals(settingType)).collect(Collectors.toList());
    }

    public static List<Setting<?>> getSettingList(Function function) {
        return getSettingList().stream().filter(setting -> setting.function.equals(function)).collect(Collectors.toList());
    }
}
