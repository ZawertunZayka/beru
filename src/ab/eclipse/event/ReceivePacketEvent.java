package ab.eclipse.event;

import net.minecraft.network.IPacket;

public class ReceivePacketEvent extends Event {
    public IPacket<?> packet;

    public Runnable callback = null;

    public ReceivePacketEvent(IPacket<?> packet) {
        this.packet = packet;
    }
}
