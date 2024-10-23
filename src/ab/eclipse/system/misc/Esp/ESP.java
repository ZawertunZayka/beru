package ab.eclipse.system.misc.Esp;

import ab.eclipse.system.utils.DisplayUtils;
import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.scoreboard.Score;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector4f;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ESP {
    private static final Minecraft mc = Minecraft.getInstance();
    private final HashMap<Entity, Vector4f> positions = new HashMap<>();

    @Subscribe
    public void onDisplay(EventDisplay event) {
        if (mc.world != null && event.getType() == EventDisplay.Type.PRE) {
            this.positions.clear();
            Iterator<Entity> entityIterator = mc.world.getAllEntities().iterator();

            while (entityIterator.hasNext()) {
                Entity entity = entityIterator.next();
                if (isValid(entity)) {
                    double x = interpolate(entity.getPosX(), entity.lastTickPosX, event.getPartialTicks());
                    double y = interpolate(entity.getPosY(), entity.lastTickPosY, event.getPartialTicks());
                    double z = interpolate(entity.getPosZ(), entity.lastTickPosZ, event.getPartialTicks());
                    Vector3d entitySize = new Vector3d(entity.getBoundingBox().maxX - entity.getBoundingBox().minX,
                            entity.getBoundingBox().maxY - entity.getBoundingBox().minY,
                            entity.getBoundingBox().maxZ - entity.getBoundingBox().minZ);
                    AxisAlignedBB boundingBox = new AxisAlignedBB(x - entitySize.x / 2.0, y, z - entitySize.z / 2.0,
                            x + entitySize.x / 2.0, y + entitySize.y, z + entitySize.z / 2.0);
                    Vector4f projectedPosition = projectBoundingBox(boundingBox);
                    this.positions.put(entity, projectedPosition);
                }
            }
            renderESP();
        }
    }

    private void renderESP() {
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(7425);

        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_COLOR);

        for (Map.Entry<Entity, Vector4f> entry : positions.entrySet()) {
            Entity entity = entry.getKey();
            Vector4f pos = entry.getValue();

            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                renderBox(livingEntity, pos);
                renderHealthBar(livingEntity, pos);
            } else if (entity instanceof ItemEntity) {
                renderItem((ItemEntity) entity, pos);
            }
        }

        tessellator.draw();
        RenderSystem.shadeModel(7424);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    private void renderBox(LivingEntity entity, Vector4f pos) {
        // Отрисовка бокса вокруг сущности
        DisplayUtils.drawBox(pos.getX(), pos.getY(), pos.getZ(), pos.getW(), 1.0, ColorUtils.rgba(255, 0, 0, 255));
    }

    private void renderHealthBar(LivingEntity entity, Vector4f pos) {
        // Отрисовка полоски здоровья
        float health = entity.getHealth();
        float maxHealth = entity.getMaxHealth();
        float healthPercentage = health / maxHealth;
        DisplayUtils.drawHealthBar(pos, healthPercentage);
    }

    private void renderItem(ItemEntity itemEntity, Vector4f pos) {
        ItemStack stack = itemEntity.getItem();
        String itemName = stack.getDisplayName().getString();
        float itemNameWidth = mc.fontRenderer.getStringWidth(itemName);
        GL11.glPushMatrix();
        glCenteredScale(pos.getX() + (pos.getZ() - pos.getX()) / 2.0F - itemNameWidth / 2.0F, pos.getY() - 7.0F, itemNameWidth, 10.0F, 0.5F);
        mc.fontRenderer.drawStringWithShadow(itemName, pos.getX() + (pos.getZ() - pos.getX()) / 2.0F - itemNameWidth / 2.0F, pos.getY() - 7.0F, -1);
        GL11.glPopMatrix();
    }

    private Vector4f projectBoundingBox(AxisAlignedBB boundingBox) {
        // Используем координаты bounding box для проекции
        float x = (float) boundingBox.minX;
        float y = (float) boundingBox.minY;
        float z = (float) boundingBox.maxX;
        float w = (float) boundingBox.maxY;
        return new Vector4f(x, y, z, w);
    }

    private double interpolate(double now, double last, float partialTicks) {
        return last + (now - last) * partialTicks;
    }

    public boolean isValid(Entity entity) {
        return entity instanceof LivingEntity || entity instanceof ItemEntity;
    }

    public void glCenteredScale(float x, float y, float width, float height, float scale) {
        GL11.glTranslatef(x + width / 2.0F, y + height / 2.0F, 0.0F);
        GL11.glScalef(scale, scale, 1.0F);
        GL11.glTranslatef(-x - width / 2.0F, -y - height / 2.0F, 0.0F);
    }
}
