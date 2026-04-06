package domain;

public enum Index {

    BOARD_ROWS(10),
    BOARD_COLUMNS(9);

    private final int index;

    Index(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
