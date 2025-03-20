package domain;

public enum Team {
    RED(1, 1),
    BLUE(10, 1);

    private final int initRow;
    private final int initColumn;

    Team(int initRow, int initColumn) {
        this.initRow = initRow;
        this.initColumn = initColumn;
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
