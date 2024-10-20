package ab.eclipse.event;

import net.minecraft.util.text.ITextComponent;
import org.w3c.dom.Text;

public class ReceiveMessageEvent extends Event {
    public String message;

    public ReceiveMessageEvent(String message) {
        this.message = message;
    }
}
