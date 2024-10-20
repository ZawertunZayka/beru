package ab.eclipse.autobuy;


import ab.eclipse.EclipseFuntime;
import ab.eclipse.autobuy.manager.AutoBuyItem;
import ab.eclipse.autobuy.manager.AutoBuyManager;
import ab.eclipse.autobuy.manager.CustomAutoBuyItem;
import ab.eclipse.autobuy.manager.DefaultAutoBuyItem;
import ab.eclipse.utils.font.main.IFont;
import ab.eclipse.utils.render.Render2d;
import net.minecraft.item.Item;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static ab.eclipse.EclipseFuntime.mc;


public class SelectWindowButton {
    public double x, y, w, h;
    public Item item;
    public String name;
    public CustomAutoBuyItem customAutoBuyItem = null;

    public SelectWindowButton(double x, double y, double w, double h, Item item, String name, CustomAutoBuyItem customAutoBuyItem) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.item = item;
        this.name = name;
        this.customAutoBuyItem = customAutoBuyItem;
    }

    public void render(double mx, double my) {
        Render2d.drawRoundedRect(x + 4, y, w - 8, h, 2, new Color(16, 15, 15, 255));
        IFont.drawCenteredY(IFont.MONTSERRAT_MEDIUM, name, (float) (x + 28), (float) (y + h / 2), Color.WHITE, 8);
        IFont.drawCenteredY(IFont.MONTSERRAT_MEDIUM, " + ", (float) (x + w - 8 - IFont.getWidth(IFont.MONTSERRAT_MEDIUM, " + ", 8)), (float) (y + h / 2), Color.GREEN, 8);
        GL11.glPushMatrix();
        GL11.glTranslated(x + 8, y + h / 2 - 8, 0);
        mc.getItemRenderer().renderItemIntoGUI(item.getDefaultInstance(), 0, 0);
        GL11.glPopMatrix();
    }

    public void click(double mx, double my, int b) {
        if (AutoBuyGui.isHover(x + w - 32, y, 32, h, mx, my) && b == GLFW.GLFW_MOUSE_BUTTON_1) {
            AutoBuyItem newItem;
            if (customAutoBuyItem != null) {
                newItem = new CustomAutoBuyItem(item, 1000);
                ((CustomAutoBuyItem) newItem).name = customAutoBuyItem.name;
                ((CustomAutoBuyItem) newItem).line = customAutoBuyItem.line;
                ((CustomAutoBuyItem) newItem).enchantments = customAutoBuyItem.enchantments;
                ((CustomAutoBuyItem) newItem).attributes = customAutoBuyItem.attributes;
                ((CustomAutoBuyItem) newItem).effect = customAutoBuyItem.effect;
                ((CustomAutoBuyItem) newItem).strictCheck = customAutoBuyItem.strictCheck;
            } else {
                newItem = new DefaultAutoBuyItem(item, 1000);
            }
            AutoBuyManager.addItem(newItem);
            EclipseFuntime.abGui.addItem(newItem);
        }
    }
}