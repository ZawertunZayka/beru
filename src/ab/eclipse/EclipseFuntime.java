package ab.eclipse;

import ab.eclipse.autobuy.AutoBuy;
import ab.eclipse.autobuy.AutoBuyGui;
import ab.eclipse.autobuy.manager.AutoBuyManager;
import ab.eclipse.autobuy.manager.IgnoreManager;
import ab.eclipse.gui.MainScreen;
import ab.eclipse.impl.function.FunctionManager;
import ab.eclipse.system.altmanager.AltManager;
import ab.eclipse.system.misc.SimpleFunction;
import ab.eclipse.system.misc.autoloot.AutoLoot;
import ab.eclipse.system.misc.automyst.AutoMyst;
import ab.eclipse.system.misc.casino.Casino;
import ab.eclipse.utils.font.main.IFont;
import com.google.common.eventbus.EventBus;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.ITextComponent;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EclipseFuntime {
    public static long initTime;

    public static EventBus eventBus = new EventBus();
    public static Minecraft mc;
    public static MainScreen gui;
    public static AutoBuy autoBuy = new AutoBuy();
    public static AutoBuyGui abGui;

    public static AutoLoot autoLoot = new AutoLoot();
    public static Casino casino = new Casino();
    public static AutoMyst autoMyst = new AutoMyst();
    public static List<SimpleFunction> functionList = new ArrayList<>();

    public static void init(EclipseFuntime eclipseFuntime) {
        File mainFolder = new File("\\ab.eclipse\\");

        if (!mainFolder.exists()) {
            mainFolder.mkdirs();
        }

        mc = Minecraft.getInstance();
        initTime = System.currentTimeMillis();

        AutoBuyManager.init();

        FunctionManager.init();
        AutoBuyManager.load();
        AltManager.load();
        IgnoreManager.load();
        load();

        IFont.init();

        eventBus.register(eclipseFuntime);
        eventBus.register(autoBuy);

        functionList.add(autoLoot);
        functionList.add(casino);
        functionList.add(autoMyst);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            AutoBuyManager.save();
            AltManager.save();
            IgnoreManager.save();
            save();
        }));
    }

    public static void load() {
        File file = new File("\\ab.eclipse\\function.txt");

        if (!file.exists()) {
            return;
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String s = line.split(":")[0];
                String s2 = line.split(":")[1];
                String s3 = line.split(":")[2];
                if (s.equals("casino")) {
                    casino.bind = Integer.parseInt(s2);
                    casino.toggled = Boolean.parseBoolean(s3);
                    if (casino.toggled) {
                        eventBus.register(casino);
                    }
                }
                if (s.equals("automyst")) {
                    autoMyst.bind = Integer.parseInt(s2);
                    autoMyst.toggled = Boolean.parseBoolean(s3);
                    if (autoMyst.toggled) {
                        eventBus.register(autoMyst);
                    }
                }
                if (s.equals("autoloot")) {
                    autoLoot.bind = Integer.parseInt(s2);
                    autoLoot.toggled = Boolean.parseBoolean(s3);
                    if (autoLoot.toggled) {
                        eventBus.register(autoLoot);
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void save() {
        File file = new File("\\ab.eclipse\\function.txt");

        if (file.exists()) {
            file.delete();
        }

        try {
            file.createNewFile();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write("autoloot:" + autoLoot.bind + ":" + autoLoot.toggled + "\n");
            bufferedWriter.write("automyst:" + autoMyst.bind + ":" + autoMyst.toggled + "\n");
            bufferedWriter.write("casino:" + casino.bind + ":" + casino.toggled + "\n");
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean canUpdate() {
        if (mc == null) return false;
        return mc.player != null && mc.world != null;
    }
}
