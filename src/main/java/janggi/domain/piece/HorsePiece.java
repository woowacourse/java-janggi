package janggi.domain.piece;

import janggi.domain.board.Position;

public class HorsePiece extends Piece {
    public HorsePiece(Team team) {
        super(team, Name.HORSE);
    }


    @Override
    public boolean canMove(Position from, Position to) {
        return false;
    }
}
