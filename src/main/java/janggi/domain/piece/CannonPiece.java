package janggi.domain.piece;

import janggi.domain.board.Position;

public class CannonPiece extends Piece {
    public CannonPiece(Team team) {
        super(team, Name.CANNON);
    }

    @Override
    public boolean canMove(Position from, Position to) {
        return false;
    }
}
