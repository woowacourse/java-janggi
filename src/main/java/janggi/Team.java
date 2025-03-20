package janggi;

public enum Team {
    RED,
    GREEN,
    ;

    public static int decideRow(final int row, final Team team) {
        if (team.isGreen()) {
            int rowFlipBase = 11;
            return rowFlipBase - row;
        }
        return row;
    }

    public boolean isRed() {
        return this == RED;
    }

    public boolean isGreen() {
        return this == GREEN;
    }
}
