package janggi.model;

public enum Color {
    RED(1),
    BLUE(10),
    ;

    private final int initRow;

    Color(int initRow) {
        this.initRow = initRow;
    }

    public int getInitRow() {
        return initRow;
    }

    public Color reverse() {
        if (this == Color.RED) {
            return Color.BLUE;
        }
        return Color.RED;
    }
}
