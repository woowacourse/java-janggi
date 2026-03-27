package domain.piece;

import domain.Board;
import domain.PieceType;
import domain.Position;
import domain.Team;

public class King extends Piece {
    public King(Team team) {
        super(team, PieceType.KING);
    }


    @Override
    boolean canMove(Position from, Position to, Board board) {
        return false;
    }
}
