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
        switchTurn();
        return turn;
    }

    private void switchTurn() {
        if (turn == Team.RED) {
            turn = Team.BLUE;
            return;
        }
        if (turn == Team.BLUE) {
            turn = Team.RED;
        }
    }
}
