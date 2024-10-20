package ab.eclipse.system.misc.casino;

import ab.eclipse.event.ReceiveMessageEvent;
import ab.eclipse.event.TickEvent;
import ab.eclipse.system.misc.SimpleFunction;
import com.google.common.eventbus.Subscribe;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ab.eclipse.EclipseFuntime.mc;


public class Casino extends SimpleFunction {
    public long sendMessageDelay, changeAnDelay, lastMessageSend;
    public List<Runnable> callbacks = new ArrayList<>();
    public int count;

    public Casino() {
        super("Casino");
    }

    public void on() {
        count = 0;
        callbacks.clear();
        lastMessageSend = 0;
        sendMessageDelay = 0;
        changeAnDelay = System.currentTimeMillis() + 60000 * 5;
    }

    @Subscribe
    private void onReceiveMessageEvent(ReceiveMessageEvent event) {
        String message = event.message;

        if (message.contains("отправил вам") && message.startsWith("▶")) {
            String[] args = message.split(" ");
            String player = args[2];
            int p = (int) Double.parseDouble(args[5]);
            float r = new Random().nextFloat();

            if (p < 10000) return;

            count++;

            if (r < 0.25f) {
                callbacks.add(() -> {
                    mc.player.sendChatMessage("/msg " + player + " удача вас любит) вы заработали " + (p + (p * (0.25f))));
                    mc.player.sendChatMessage("/pay " + player + " " + (p + (p * (0.25f))));
                });
            } else {
                callbacks.add(() -> mc.player.sendChatMessage("/msg " + player + " к сожалению вы проиграли("));
            }
        }
    }

    @Subscribe
    private void onTick(TickEvent event) {
        if (!callbacks.isEmpty() && (System.currentTimeMillis() - lastMessageSend > 8000)) {
            callbacks.get(0).run();
            callbacks.remove(0);
            lastMessageSend = System.currentTimeMillis();
        }

        if (System.currentTimeMillis() > sendMessageDelay) {
            mc.player.sendChatMessage("!Привет, я казино бот! Испытай свою удачу и заработай монет! /pay " + mc.getSession().getUsername() + " (сумма), если тебе повезет то ты сможешь умножить свои деньги! (мин ставка = 10000) игр сыграно - " + count);
            sendMessageDelay = System.currentTimeMillis() + 30000L;
            lastMessageSend = System.currentTimeMillis();
        }
    }
}