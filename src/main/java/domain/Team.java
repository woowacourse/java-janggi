package domain;

public enum Team {
    RED(1),
    BLUE(10);

    private final int initRow;

    Team(final int initRow) {
        this.initRow = initRow;
    }

    public int getInitRow() {
        return initRow;
    }

    public int convertRowOffsetByTeam(int offset) {
        if (this == Team.BLUE) {
            return -offset;
        }
        return offset;
    }
}
