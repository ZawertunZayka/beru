package ab.eclipse.event;

import lombok.Getter;

@Getter
public class Event {
    private boolean cancelled = false;

    public void cancel() {
        cancelled = true;
    }

}
