package domain.piece;

import domain.Board;
import domain.PieceType;
import domain.Position;
import domain.Team;

public class Rook extends Piece{
    public Rook(Team team) {
        super(team, PieceType.ROOK);
    }

    @Override
    boolean canMove(Position from, Position to, Board board) {
        return false;
    }
}
