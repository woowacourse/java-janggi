package domain;

public enum Color {
    RED(1),
    BLUE(10);

    private final int initRow;

    Color(final int initRow) {
        this.initRow = initRow;
    }

    public int getInitRow() {
        return initRow;
    }

    public int convertRowOffsetByTeam(int offset) {
        if (this == Color.BLUE) {
            return -offset;
        }
        return offset;
    }
}
