package janggi.db;

public enum BoardStatusColumn {
    NAME_PIECE(1),
    NAME_TEAM(2),
    NAME_STATUS(3),
    POSITION_X(4),
    POSITION_Y(5);

    private final int index;

    BoardStatusColumn(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
