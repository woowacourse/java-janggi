package domain.state;

public enum Side {

    HAN(0, 1.5),
    CHU(9, 0),
    NEUTRAL(-1, 0);

    private final int startingRow;
    private final double bonusScore;
    private Side opposite;

    Side(int startingRow, double bonusScore) {
        this.startingRow = startingRow;
        this.bonusScore = bonusScore;
    }

    static {
        HAN.opposite = CHU;
        CHU.opposite = HAN;
        NEUTRAL.opposite = NEUTRAL;
    }

    public static GameState firstMoveSide() {
        return new ChuSide();
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

    public double getBonusScore() {
        return bonusScore;
    }
}
