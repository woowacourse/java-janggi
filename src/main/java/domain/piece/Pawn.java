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
        /**
         * TODO:
         * 2. 판의 범위를 넘어서는 경우 예외 -> 옆인지, 앞인지로 분기
         * 3. 초나라 구현 후 한나라 상황을 고려하여 수정한다.
         */

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
            return Math.abs(from.columnDistanceTo(to)) == 1 || from.rowDistanceTo(to) == -1;
        }
        return Math.abs(from.columnDistanceTo(to)) == 1 || from.rowDistanceTo(to) == 1;
    }
}
