package domain.piece;

import domain.Board;
import domain.Palace;
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
        int rowDifference = from.rowDistanceTo(to);
        int columnDifference = from.columnDistanceTo(to);
        int forward = forwardDirection();

        boolean isStraightMove = (Math.abs(from.columnDistanceTo(to)) == 1 && from.rowDistanceTo(to) == 0)
                || (from.rowDistanceTo(to) == forward && from.columnDistanceTo(to) == 0);

        boolean isDiagonalMove = Palace.isPalace(from)
                && Palace.isPalace(to)
                && rowDifference == forward
                && Math.abs(columnDifference) == 1;

        return isDiagonalMove || isStraightMove ;
    }

    private int forwardDirection() {
        if (isSameTeam(Team.CHO)) {
            return -1;
        }
        return 1;
    }
}
