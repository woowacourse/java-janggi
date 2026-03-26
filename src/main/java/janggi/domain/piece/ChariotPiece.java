package janggi.domain.piece;

import janggi.domain.board.Position;

public class ChariotPiece extends Piece {
    public ChariotPiece(Team team) {
        super(team, Name.CHARIOT);
    }

    @Override
    public boolean canMove(Position from, Position to) {
        return false;
    }
}
