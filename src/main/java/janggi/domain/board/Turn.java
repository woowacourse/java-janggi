package janggi.domain.board;

import janggi.domain.piece.Team;

public class Turn {
    private final Team team;

    public Turn(Team team) {
        this.team = team;
    }

    public Turn changeTurn() {
        if (team == Team.HAN) {
            return new Turn(Team.CHO);
        }
        return new Turn(Team.HAN);
    }

    public boolean isCurrentTeam(Team team) {
        return this.team == team;
    }

    public Team team() {
        return team;
    }
}
