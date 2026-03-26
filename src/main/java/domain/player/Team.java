package domain.player;

public enum Team {
    CHO(0),
    HAN(9),
    NULL(-1);

    private final int column;

    Team(int column) {
        this.column = column;
    }

    public boolean isHan() {
        return this == HAN;
    }

    public boolean isCho() {
        return this == CHO;
    }

    public int getColumn() {
        return column;
    }
}
