package janggi.domain;

public enum Team {

    RED,
    BLUE;

    public static Team getOtherTeam(final Team team) {
        if (team == RED) {
            return BLUE;
        }
        return RED;
    }


}
