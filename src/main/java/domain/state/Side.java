package domain.state;

public enum Side {

    HAN(0),
    CHU(9),
    NEUTRAL(-1);

    private final int startingRow;
    private Side opposite;

    Side(int startingRow) {
        this.startingRow = startingRow;
    }

    static {
        HAN.opposite = CHU;
        CHU.opposite = HAN;
        NEUTRAL.opposite = NEUTRAL;
    }

    public boolean isNeutral() {
        return this == NEUTRAL;
    }

    public Side opposite() {
        return opposite;
    }

    public int getStartingRow() {
        return startingRow;
    }
}
