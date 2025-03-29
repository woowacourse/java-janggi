package janggi.piece;

public enum Team {
    GREEN("초", 0),
    RED("한", 1.5);

    private final String displayName;
    private final double bonusPoint;

    Team(String displayName, double bonusPoint) {
        this.displayName = displayName;
        this.bonusPoint = bonusPoint;
    }

    public String getName() {
        return displayName;
    }

    public double getBonusPoint() {
        return bonusPoint;
    }

    public Team convertTeam() {
        if (this == GREEN) {
            return RED;
        }
        return GREEN;
    }
}
