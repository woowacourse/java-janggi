package domain.janggi.domain;

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

}
