package ab.eclipse.autobuy;

import ab.eclipse.system.misc.SimpleFunction;
import ab.eclipse.utils.font.main.IFont;
import ab.eclipse.utils.render.Render2d;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;

import static org.lwjgl.glfw.GLFW.*;

public class FunctionButton {
    public double x, y, w, h;
    public SimpleFunction function;
    public boolean binding = false;

    public FunctionButton(double x, double y, double w, double h, SimpleFunction function) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.function = function;
    }

    public void render() {
        Render2d.drawRoundedRect(x + 4, y, w - 8, h, 2, new Color(16, 15, 15, 255));
        IFont.drawCenteredY(IFont.MONTSERRAT_MEDIUM, function.name, (float) (x + 8), (float) (y + h / 2), function.toggled ? Color.GREEN : Color.RED, 8);
        String s = binding ? "..." : getKeyName(function.bind);
        IFont.drawCenteredY(IFont.MONTSERRAT_MEDIUM, "Бинд: " + s, (float) (x + w - 8 - IFont.getWidth(IFont.MONTSERRAT_MEDIUM, "Бинд: " + s, 8)), (float) (y + h / 2), Color.WHITE, 8);
    }

    public String getKeyName(int key) {
        switch (key) {
            case GLFW_MOUSE_BUTTON_1 :{
                return "M1";
            }
            case GLFW_MOUSE_BUTTON_2 : {
                return "M2";
            }
            case GLFW_MOUSE_BUTTON_3 : {
                return "M3";
            }
            case GLFW_MOUSE_BUTTON_4 : {
                return "M4";
            }
            case GLFW_MOUSE_BUTTON_5 : {
                return "M5";
            }
            case GLFW_KEY_UNKNOWN : {
                return "?";
            }
            case GLFW_KEY_ESCAPE : {
                return "ESC";
            }
            case GLFW_KEY_GRAVE_ACCENT : {
                return "Grave Accent";
            }
            case GLFW_KEY_WORLD_1 : {
                return "World 1";
            }
            case GLFW_KEY_WORLD_2 : {
                return "World 2";
            }
            case GLFW_KEY_PRINT_SCREEN : {
                return "Print Screen";
            }
            case GLFW_KEY_PAUSE : {
                return "Pause";
            }
            case GLFW_KEY_INSERT : {
                return "Insert";
            }
            case GLFW_KEY_DELETE : {
                return "Delete";
            }
            case GLFW_KEY_HOME : {
                return "Home";
            }
            case GLFW_KEY_PAGE_UP : {
                return "Page Up";
            }
            case GLFW_KEY_PAGE_DOWN : {
                return "Page Down";
            }
            case GLFW_KEY_END : {
                return "End";
            }
            case GLFW_KEY_TAB : {
                return "Tab";
            }
            case GLFW_KEY_LEFT_CONTROL : {
                return "Left Control";
            }
            case GLFW_KEY_RIGHT_CONTROL : {
                return "Right Control";
            }
            case GLFW_KEY_LEFT_ALT : {
                return "Left Alt";
            }
            case GLFW_KEY_RIGHT_ALT : {
                return "Right Alt";
            }
            case GLFW_KEY_LEFT_SHIFT : {
                return "Left Shift";
            }
            case GLFW_KEY_RIGHT_SHIFT : {
                return "Right Shift";
            }
            case GLFW_KEY_UP : {
                return "Arrow Up";
            }
            case GLFW_KEY_DOWN : {
                return "Arrow Down";
            }
            case GLFW_KEY_LEFT : {
                return "Arrow Left";
            }
            case GLFW_KEY_RIGHT : {
                return "Arrow Right";
            }
            case GLFW_KEY_APOSTROPHE : {
                return "Apostrophe";
            }
            case GLFW_KEY_BACKSPACE : {
                return "Backspace";
            }
            case GLFW_KEY_CAPS_LOCK : {
                return "Caps Lock";
            }
            case GLFW_KEY_MENU : {
                return "Menu";
            }
            case GLFW_KEY_LEFT_SUPER : {
                return "Left Super";
            }
            case GLFW_KEY_RIGHT_SUPER : {
                return "Right Super";
            }
            case GLFW_KEY_ENTER : {
                return "Enter";
            }
            case GLFW_KEY_KP_ENTER : {
                return "Numpad Enter";
            }
            case GLFW_KEY_NUM_LOCK : {
                return "Num Lock";
            }
            case GLFW_KEY_SPACE : {
                return "Space";
            }
            case GLFW_KEY_F1 : {
                return "F1";
            }
            case GLFW_KEY_F2 : {
                return "F2";
            }
            case GLFW_KEY_F3 : {
                return "F3";
            }
            case GLFW_KEY_F4 : {
                return "F4";
            }
            case GLFW_KEY_F5 : {
                return "F5";
            }
            case GLFW_KEY_F6 : {
                return "F6";
            }
            case GLFW_KEY_F7 : {
                return "F7";
            }
            case GLFW_KEY_F8 : {
                return "F8";
            }
            case GLFW_KEY_F9 : {
                return "F9";
            }
            case GLFW_KEY_F10 : {
                return "F10";
            }
            case GLFW_KEY_F11 : {
                return "F11";
            }
            case GLFW_KEY_F12 : {
                return "F12";
            }
            case GLFW_KEY_F13 : {
                return "F13";
            }
            case GLFW_KEY_F14 : {
                return "F14";
            }
            case GLFW_KEY_F15 : {
                return "F15";
            }
            case GLFW_KEY_F16 : {
                return "F16";
            }
            case GLFW_KEY_F17 : {
                return "F17";
            }
            case GLFW_KEY_F18 : {
                return "F18";
            }
            case GLFW_KEY_F19 : {
                return "F19";
            }
            case GLFW_KEY_F20 : {
                return "F20";
            }
            case GLFW_KEY_F21 : {
                return "F21";
            }
            case GLFW_KEY_F22 : {
                return "F22";
            }
            case GLFW_KEY_F23 : {
                return "F23";
            }
            case GLFW_KEY_F24 : {
                return "F24";
            }
            case GLFW_KEY_F25 : {
                return "F25";
            }
            default : {
                String keyName = glfwGetKeyName(key, 0);
                if (keyName == null) return "?";
                return StringUtils.capitalize(keyName);
            }
        }
    }

    public void click(double mx, double my, int b) {
        if (AutoBuyGui.isHover(x, y, w, h, mx, my) && b == GLFW_MOUSE_BUTTON_1) {
            binding = !binding;
        }
    }

    public void key(int key) {
        if (binding) {
            if (key == GLFW_KEY_DELETE) {
                function.bind = -1;
            } else {
                function.bind = key;
            }
            binding = false;
        }
    }
}
