package domain.team;

public enum Team {

    CHO,
    HAN,
    ;

    public boolean isSameTeam(Team team) {
        return this == team;
    }

    public Team nextTurn() {
        if (this == CHO) {
            return HAN;
        }
        return CHO;
    }

}
