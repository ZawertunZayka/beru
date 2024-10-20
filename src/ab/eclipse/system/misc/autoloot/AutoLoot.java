package ab.eclipse.system.misc.autoloot;

import ab.eclipse.event.TickEvent;
import ab.eclipse.system.misc.SimpleFunction;
import com.google.common.eventbus.Subscribe;

import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.util.Hand;

import static ab.eclipse.EclipseFuntime.mc;


public class AutoLoot extends SimpleFunction {

    public AutoLoot() {
        super("AutoLoot");
    }

    @Subscribe
    private void onTickEvent(TickEvent event) {
        for (Entity entity : mc.world.getAllEntities()) {
            if (entity instanceof VillagerEntity || entity instanceof WanderingTraderEntity) {
                if (!((AbstractVillagerEntity) entity).getHeldItemMainhand().isEmpty() && mc.player.getDistance(entity) <= mc.playerController.getBlockReachDistance()) {
                    mc.playerController.interactWithEntity(mc.player, entity, Hand.MAIN_HAND);
                    mc.player.swingArm(Hand.MAIN_HAND);
                    mc.player.sendChatMessage("/ah sell 30000000");
                }
            }
        }
    }
}