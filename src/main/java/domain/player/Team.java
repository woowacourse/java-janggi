package domain.player;

public enum Team {
    CHO(9, 0),
    HAN(0, 1.5);

    private final int column;
    private final double score;

    Team(int column, double score) {
        this.column = column;
        this.score = score;
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

    public double getScore() {
        return score;
    }
}
