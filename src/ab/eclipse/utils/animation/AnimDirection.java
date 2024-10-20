package ab.eclipse.utils.animation;

public enum AnimDirection {
    FORWARDS,
    BACKWARDS;

    public AnimDirection opposite() {
        if (this == AnimDirection.FORWARDS) {
            return AnimDirection.BACKWARDS;
        } else return AnimDirection.FORWARDS;
    }

}
