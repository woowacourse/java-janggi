package janggi.domain;

import janggi.domain.piece.Team;

public class Turn {
    private final Team firstTurn;
    private Team turn;

    private Turn(final Team turn) {
        this.firstTurn = turn;
        this.turn = turn;
    }

    public static Turn startWith(final Team team) {
        return new Turn(team);
    }

    public Team current() {
        return turn;
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

    public boolean startedBy(final Team team) {
        return firstTurn == team;
    }
}
