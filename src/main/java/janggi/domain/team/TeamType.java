package janggi.domain.team;

import janggi.domain.Position;

public enum TeamType {
    RED("한나라", 0),
    BLUE("초나라", 1.5);

    private final String name;
    private final double bonusScore;

    TeamType(final String name, final double bonusScore) {
        this.name = name;
        this.bonusScore = bonusScore;
    }

    public String getName() {
        return name;
    }

    public TeamType nextTeamType() {
        if (this == RED) {
            return BLUE;
        }
        return RED;
    }

    public Position adjustPosition(Position position) {
        if (this == BLUE) {
            return position.flipAroundMiddleRow();
        }
        return position;
    }

    public double bonusScore() {
        return bonusScore;
    }
}
