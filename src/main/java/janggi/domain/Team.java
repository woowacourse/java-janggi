package janggi.domain;

public enum Team {
    CHO,
    HAN,
    ;

    public boolean isSameTeam(Team team) {
        return this == team;
    }
}
