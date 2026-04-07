package domain.team;

public enum Team {
    CHO,
    HAN,
    NONE;

    public Team nextTurn() {
        if (this == CHO) {
            return HAN;
        }
        return CHO;
    }

    public boolean isSameTeam(Team other) {
        return this == other;
    }
}
