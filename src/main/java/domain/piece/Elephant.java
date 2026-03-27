package domain.piece;

import domain.Board;
import domain.PieceType;
import domain.Position;
import domain.Team;

public class Elephant extends Piece {
    public Elephant(Team team) {
        super(team, PieceType.ELEPHANT);
    }

    @Override
    boolean canMove(Position from, Position to, Board board) {
        return false;
    }
}
