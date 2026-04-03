package janggi.domain.piece;

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
