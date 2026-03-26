package janggi.domain.piece;

import janggi.domain.board.Position;

public class SoliderPiece extends Piece {
    public SoliderPiece(Team team) {
        super(team, Name.SOLDIER);
    }

    @Override
    public boolean canMove(Position from, Position to) {
        return false;
    }
}
