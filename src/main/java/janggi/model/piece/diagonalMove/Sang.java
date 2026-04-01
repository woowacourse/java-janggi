package janggi.model.piece.diagonalMove;

import janggi.model.Team;

public class Sang extends DiagonalMovePiece {

    private final Movement movement;

    public Sang(Team team) {
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        return movement.move(from, to);
    }
}
