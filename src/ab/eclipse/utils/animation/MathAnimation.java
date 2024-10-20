package ab.eclipse.utils.animation;


public abstract class MathAnimation {

    public StopWatch timerUtil = new StopWatch();
    protected int duration;
    protected double endPoint;
    protected AnimDirection animDirection;

    public MathAnimation(int ms, double endPoint) {
        this.duration = ms;
        this.endPoint = endPoint;
        this.animDirection = AnimDirection.FORWARDS;
    }


    public MathAnimation(int ms, double endPoint, AnimDirection animDirection) {
        this.duration = ms;
        this.endPoint = endPoint;
        this.animDirection = animDirection;
    }

    public boolean finished(AnimDirection animDirection) {
        return isDone() && this.animDirection.equals(animDirection);
    }

    public double getLinearOutput() {
        return 1 - ((timerUtil.getTime() / (double) duration) * endPoint);
    }

    public double getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(double endPoint) {
        this.endPoint = endPoint;
    }

    public void reset() {
        timerUtil.reset();
    }

    public boolean isDone() {
        return timerUtil.isReached(duration);
    }

    public void changeDirection() {
        setDirection(animDirection.opposite());
    }

    public AnimDirection getDirection() {
        return animDirection;
    }

    public void setDirection(AnimDirection animDirection) {
        if (this.animDirection != animDirection) {
            this.animDirection = animDirection;
            timerUtil.setTime(System.currentTimeMillis() - (duration - Math.min(duration, timerUtil.getTime())));
        }
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    protected boolean correctOutput() {
        return false;
    }

    public double getOutput() {
        if (animDirection == AnimDirection.FORWARDS) {
            if (isDone())
                return endPoint;
            return (getEquation(timerUtil.getTime()) * endPoint);
        } else {
            if (isDone()) return 0;
            if (correctOutput()) {
                double revTime = Math.min(duration, Math.max(0, duration - timerUtil.getTime()));
                return getEquation(revTime) * endPoint;
            } else return (1 - getEquation(timerUtil.getTime())) * endPoint;
        }
    }


    //This is where the animation equation should go, for example, a logistic function. Output should range from 0 - 1.
    //This will take the timer's time as an input, x.
    protected abstract double getEquation(double x);

}
