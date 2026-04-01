package domain.board;

public enum Side {

    HAN,
    CHU,
    NEUTRAL;

    public boolean isNeutral() {
        return this == NEUTRAL;
    }

    public Side change() {
        if (this == HAN) {
            return CHU;
        }

        return HAN;
    }

    public int getStartingRow() {
        if (this == Side.HAN) {
            return 0;
        }

        return 9;
    }
}
