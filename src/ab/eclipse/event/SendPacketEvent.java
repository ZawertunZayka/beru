package ab.eclipse.event;

import net.minecraft.network.IPacket;

public class SendPacketEvent extends Event {
    public IPacket<?> packet;

    public SendPacketEvent(IPacket<?> packet) {
        this.packet = packet;
    }
}
