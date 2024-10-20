package ab.eclipse.utils.animation.impl;


import ab.eclipse.utils.animation.AnimDirection;
import ab.eclipse.utils.animation.MathAnimation;

public class EaseBackIn extends MathAnimation {
    private final float easeAmount;

    public EaseBackIn(int ms, double endPoint, float easeAmount) {
        super(ms, endPoint);
        this.easeAmount = easeAmount;
    }

    public EaseBackIn(int ms, double endPoint, float easeAmount, AnimDirection animDirection) {
        super(ms, endPoint, animDirection);
        this.easeAmount = easeAmount;
    }

    @Override
    protected boolean correctOutput() {
        return true;
    }

    @Override
    protected double getEquation(double x) {
        double x1 = x / duration;
        float shrink = easeAmount + 1;
        return Math.max(0, 1 + shrink * Math.pow(x1 - 1, 3) + easeAmount * Math.pow(x1 - 1, 2));
    }

}
