package janggi.domain;

public enum Team {
    RED(73.5),
    GREEN(72),
    ;

    private static final int FLIP_ROW_BASE = 11;

    private final double initScore;

    Team(final double initScore) {
        this.initScore = initScore;
    }

    public int decideRow(final int row) {
        if (isGreen()) {
            return FLIP_ROW_BASE - row;
        }
        return row;
    }

    public boolean isRed() {
        return this == RED;
    }

    public boolean isGreen() {
        return this == GREEN;
    }

    public Team getEnemy() {
        if (isRed()) {
            return GREEN;
        }
        return RED;
    }

    public double getInitScore() {
        return initScore;
    }
}
