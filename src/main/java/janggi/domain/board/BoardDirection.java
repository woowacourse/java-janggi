package janggi.domain.board;

import janggi.domain.piece.Team;

public enum BoardDirection {
    UP(1),
    DOWN(-1);

    private final int direction;

    BoardDirection(final int direction) {
        this.direction = direction;
    }

    public boolean isForward(int direction) {
        return this.direction == direction;
    }

    public static BoardDirection of(Team team) {
        if (team == Team.HAN) {
            return UP;
        }
        return DOWN;
    }
}
