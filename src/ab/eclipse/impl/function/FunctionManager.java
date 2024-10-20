package ab.eclipse.impl.function;

import ab.eclipse.system.setting.settings.multiboolean.MultiBooleanValue;
import lombok.Getter;

import org.lwjgl.glfw.GLFW;

import java.awt.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class FunctionManager {
    @Getter
    private static final List<Function> functionList = new ArrayList<>();

    public static void init() {

        boolean first = true;
        for (Category value : Category.values()) {
            for (int i = 0; i < new Random().nextInt(15); i++) {
                Function function = new Function(value, value.name() + i);

                if (first) {
                    function.Boolean().setName("TestBoolean").setValue(true).build();
                    function.Color().setName("TestColor").setValue(Color.WHITE).build();
                    function.Integer().setName("TestInt").setValue(10).setMax(100).setMin(0).build();
                    function.Double().setName("TestDouble").setValue(10.0).setMax(100).setMin(0).build();
                    function.MultiBoolean().setName("TestMultiBoolean").setValue(List.of(
                            new MultiBooleanValue(true, "test1"),
                            new MultiBooleanValue(false, "test2"),
                            new MultiBooleanValue(false, "test3"),
                            new MultiBooleanValue(true, "test4")
                    )).build();
                    function.List().setName("TestList").setValue("Test3").setList(List.of("Test1", "Test2", "Test3")).build();
                    function.String().setName("TestString").setValue("Protected").build();
                    function.Keybind().setName("TestKeybind").setValue(GLFW.GLFW_KEY_U).build();
                    first = false;
                }

                functionList.add(function);
            }
        }

    }

    public static List<Function> getFunctionList(Category category) {
        return getFunctionList().stream().filter(f -> f.getCategory().equals(category)).collect(Collectors.toList());
    }
}
