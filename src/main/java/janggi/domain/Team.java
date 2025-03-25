package janggi.domain;

public enum Team {
    RED,
    GREEN,
    ;

    private static final int FLIP_ROW_BASE = 11;

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
}
