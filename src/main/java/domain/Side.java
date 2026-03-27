package domain;

public enum Side {

    HAN(1),
    CHU(-1),
    NEUTRAL(0);

    private final int forward;

    Side(int forward) {
        this.forward = forward;
    }

    public boolean isChu() {
        return this == CHU;
    }

    public boolean isHan() {
        return this == HAN;
    }

    public boolean isNeutral() {
        return this == NEUTRAL;
    }

    public Side change() {
        if (this == HAN) {
            return CHU;
        }

        return HAN;
    }

    public int getForward() {
        return this.forward;
    }
}
