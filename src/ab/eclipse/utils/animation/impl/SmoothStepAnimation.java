package ab.eclipse.utils.animation.impl;


import ab.eclipse.utils.animation.AnimDirection;
import ab.eclipse.utils.animation.MathAnimation;

public class SmoothStepAnimation extends MathAnimation {

    public SmoothStepAnimation(int ms, double endPoint) {
        super(ms, endPoint);
    }

    public SmoothStepAnimation(int ms, double endPoint, AnimDirection direction) {
        super(ms, endPoint, direction);
    }

    protected double getEquation(double x) {
        double x1 = x / (double) duration;
        return -2 * Math.pow(x1, 3) + (3 * Math.pow(x1, 2));
    }
}