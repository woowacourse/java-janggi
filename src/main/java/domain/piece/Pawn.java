package domain.piece;

import domain.Board;
import domain.PieceType;
import domain.Position;
import domain.Team;

public class Pawn extends Piece {
    public Pawn(Team team) {
        super(team, PieceType.PAWN);
    }

    @Override
    public boolean canMove(Position from, Position to, Board board) {
        if (!isCorrectMoveDistanceAndDirection(from, to)) {
            return false;
        }

        if (isEmptySpace(to, board)) {
            return true;
        }

        return !board.hasSameTeamOn(to, this);
    }

    private boolean isEmptySpace(Position to, Board board) {
        return board.isEmpty(to);
    }

    private boolean isCorrectMoveDistanceAndDirection(Position from, Position to) {
        if (team == Team.CHO) {
            return (Math.abs(from.columnDistanceTo(to)) == 1 && from.rowDistanceTo(to) == 0) || (from.rowDistanceTo(to) == -1 && from.columnDistanceTo(to) == 0) ;
        }
        return (Math.abs(from.columnDistanceTo(to)) == 1 && from.rowDistanceTo(to) == 0) || (from.rowDistanceTo(to) == 1 && from.columnDistanceTo(to) == 0) ;
    }
}
