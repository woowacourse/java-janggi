package domain.player;

public enum Team {
    CHO(9),
    HAN(0);

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

    public Team opposite() {
        if (this == HAN) {
            return CHO;
        }

        return HAN;
    }
}
