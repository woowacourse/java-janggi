package janggi.domain.common;

import janggi.domain.board.Palace;

public enum Team {
    CHO("초나라", 0.0),
    HAN("한나라", 1.5);

    private static final int MAX_Y = 11;
    private final String name;
    private final double startScore;

    Team(String name, double startScore) {
        this.name = name;
        this.startScore = startScore;
    }

    public String getName() {
        return name;
    }

    public int calculateYPosition(int defaultY) {
        if (this == CHO) {
            return defaultY;
        }
        return MAX_Y - defaultY;
    }

    public Team next() {
        if (this == CHO) {
            return HAN;
        }
        return CHO;
    }

    public Palace selectPalace() {
        if (this == CHO) {
            return Palace.CHO;
        }
        return Palace.HAN;
    }

    public double selectStartScoreByTeam() {
        return startScore;
    }
}
