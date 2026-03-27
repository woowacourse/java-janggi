package domain.piece;

import domain.Board;
import domain.PieceType;
import domain.Position;
import domain.Team;

public class Guard extends Piece {
    public Guard(Team team) {
        super(team, PieceType.GUARD);
    }

    @Override
    boolean canMove(Position from, Position to, Board board) {
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
        return Math.abs(from.columnDistanceTo(to)) == 1 || Math.abs(from.rowDistanceTo(to)) == 1;
    }
}
