package domain.piece;

import domain.Board;
import domain.PieceType;
import domain.Position;
import domain.Team;

public class Horse extends Piece {
    public Horse(Team team) {
        super(team, PieceType.HORSE);
    }

    @Override
    boolean canMove(Position from, Position to, Board board) {
        return false;
    }
}
