package janggi.turn;

import janggi.piece.Team;

public class Turn {

    private final Team team;

    public Turn(final Team team) {
        this.team = team;
    }

    public static Turn initialize() {
        return new Turn(Team.CHO);
    }

    public Turn moveNextTurn() {
        if (team == Team.HAN) {
            return new Turn(Team.CHO);
        }
        return new Turn(Team.HAN);
    }

    public Team getTeam() {
        return team;
    }
}
