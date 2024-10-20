package ab.eclipse.utils.animation;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;


public class AnimationUtils {
    public static float fast(float end, float start) {
        return (1 - MathHelper.clamp((float) (deltaTime() * (float) 6), 0, 1)) * end
                + MathHelper.clamp((float) (deltaTime() * (float) 6), 0, 1) * start;
    }

    public static double fast(double end, double start) {
        return (1 - MathHelper.clamp((float) (deltaTime() * (float) 6), 0, 1)) * end
                + MathHelper.clamp((float) (deltaTime() * (float) 6), 0, 1) * start;
    }

    public static float fast(float end, float start, float multiple) {
        return (1 - MathHelper.clamp((float) (deltaTime() * multiple), 0, 1)) * end
                + MathHelper.clamp((float) (deltaTime() * multiple), 0, 1) * start;
    }

    public static double fast(double end, double start, double multiple) {
        return (1 - MathHelper.clamp((float) (deltaTime() * multiple), 0, 1)) * end
                + MathHelper.clamp((float) (deltaTime() * multiple), 0, 1) * start;
    }

    public static double deltaTime() {
        return Minecraft.debugFPS > 0 ? (1.0000 / Minecraft.debugFPS) : 1;
    }
}
