package janggi.domain.piece;

import janggi.domain.board.Position;

public class GuardPiece extends Piece {
    public GuardPiece(Team team) {
        super(team, Name.GUARD);
    }

    @Override
    public boolean canMove(Position from, Position to) {
        return false;
    }
}
