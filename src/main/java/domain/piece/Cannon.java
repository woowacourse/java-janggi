package domain.piece;

import domain.Board;
import domain.PieceType;
import domain.Position;
import domain.Team;

public class Cannon extends Piece{
    public Cannon(Team team) {
        super(team, PieceType.CANNON);
    }

    @Override
    boolean canMove(Position from, Position to, Board board) {
        return false;
    }
}
