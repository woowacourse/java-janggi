package janggi.domain;

public enum Team {
    CHO(0.0),
    HAN(1.5),
    ;

    private final double handicap;

    Team(double handicap) {
        this.handicap = handicap;
    }

    public boolean isSameTeam(Team team) {
        return this == team;
    }

    public double getHandicap() {
        return handicap;
    }
}
