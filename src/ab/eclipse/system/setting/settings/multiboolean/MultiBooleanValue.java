package ab.eclipse.system.setting.settings.multiboolean;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MultiBooleanValue {
    private boolean value;
    private String name;

    public MultiBooleanValue() {
        value = false;
        name = "";
    }

    public MultiBooleanValue(boolean value, String name) {
        this.value = value;
        this.name = name;
    }
}
