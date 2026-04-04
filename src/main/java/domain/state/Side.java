package domain.state;

public enum Side {

    HAN,
    CHU,
    NEUTRAL;

    private static final int HAN_SIDE_STARTING_ROW = 0;
    private static final int CHU_SIDE_STARTING_ROW = 9;

    public boolean isNeutral() {
        return this == NEUTRAL;
    }

    public int getStartingRow() {
        if (this == HAN) {
            return HAN_SIDE_STARTING_ROW;
        }

        if (this == CHU) {
            return CHU_SIDE_STARTING_ROW;
        }

        throw new IllegalStateException("일치하는 진영이 없습니다.");
    }

    public Side opposite() {
        if (this == HAN) {
            return CHU;
        }

        if (this == CHU) {
            return HAN;
        }

        throw new IllegalStateException("일치하는 진영이 없습니다.");
    }
}
