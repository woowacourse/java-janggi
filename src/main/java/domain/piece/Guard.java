package domain.piece;

import domain.Board;
import domain.PieceType;
import domain.Position;
import domain.Team;

public class Guard extends Piece{
    public Guard(Team team) {
        super(team, PieceType.GUARD);
    }

    @Override
    boolean canMove(Position from, Position to, Board board) {
        return false;
    }
}
