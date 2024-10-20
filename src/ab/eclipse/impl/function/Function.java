package ab.eclipse.impl.function;

import ab.eclipse.EclipseFuntime;
import ab.eclipse.system.setting.settings.*;
import ab.eclipse.system.setting.settings.multiboolean.MultiBooleanSetting;
import lombok.Getter;

import net.minecraft.client.Minecraft;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Function {
    public final Minecraft mc = EclipseFuntime.mc;

    @Getter
    private Category category;

    @Getter
    private int keybind;

    @Getter
    private String name;

    @Getter
    private boolean toggled;

    public Function(Category category, String name) {
        this.category = category;
        this.name = name;
    }

    public Function() {
        Info info = getClass().getAnnotation(Info.class);
        this.keybind = info.bind();
        this.name = info.name();
        this.category = info.category();
        this.toggled = false;
    }

    public void toggled() {
        toggled = !toggled;
    }

    public BooleanSetting Boolean() {
        return new BooleanSetting(this);
    }

    public IntegerSetting Integer() {
        return new IntegerSetting(this);
    }

    public DoubleSetting Double() {
        return new DoubleSetting(this);
    }

    public ListSetting List() {
        return new ListSetting(this);
    }

    public ColorSetting Color() {
        return new ColorSetting(this);
    }

    public KeybindSetting Keybind() {
        return new KeybindSetting(this);
    }

    public MultiBooleanSetting MultiBoolean() {
        return new MultiBooleanSetting(this);
    }

    public StringSetting String() {
        return new StringSetting(this);
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Info {
        String name();

        Category category();

        int bind() default -1;
    }
}
