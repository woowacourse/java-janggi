package domain.player;

public enum Team {
    CHO(9),
    HAN(0),
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

    public boolean isNull() {
        return this == NULL;
    }

    public int getColumn() {
        return column;
    }
}
