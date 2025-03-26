package janggi.domain;

import janggi.domain.piece.Team;

public class Turn {
    private Team turn;

    private Turn(final Team turn) {
        this.turn = turn;
    }

    public static Turn startWith(final Team team) {
        return new Turn(team);
    }

    public Team next() {
        switchTeam(turn);
        return turn;
    }

    private void switchTeam(final Team currentTurn) {
        if (currentTurn == Team.RED) {
            turn = Team.BLUE;
        }
        if (currentTurn == Team.BLUE) {
            turn = Team.RED;
        }
    }
}
