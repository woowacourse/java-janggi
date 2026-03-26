package janggi.domain.piece;

import janggi.domain.board.Position;

public class GeneralPiece extends Piece {
    public GeneralPiece(Team team) {
        super(team, Name.GENERAL);
    }

    @Override
    public boolean canMove(Position from, Position to) {
        return false;
    }
}
