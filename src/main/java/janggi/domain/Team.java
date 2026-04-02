package janggi.domain;

public enum Team {
    CHO(),
    HAN();

    public Team switchTeam() {
        if (this == Team.CHO) {
            return Team.HAN;
        }
        return Team.CHO;
    }
}
