package janggi.model.piece.diagonalMove;

import janggi.model.Team;

public class Ma extends DiagonalMovePiece {

    private final Movement movement;


    public Ma(Team team) {
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        return movement.move(from, to);
    }
}
