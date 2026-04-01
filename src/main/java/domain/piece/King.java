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
    public boolean canMove(Position from, Position to, Board board) {
        if (!isPossiblePosition(to, board)) {
            return false;
        }

        if (!isCorrectMoveDistance(from, to)) {
            return false;
        }

        if (!isCorrectDirection(from, to, board)) {
            return false;
        }

        if (isEmptySpace(to, board)) {
            return true;
        }

        return !board.hasSameTeamOn(to, this);
    }

    private boolean isPossiblePosition(Position to, Board board) {
        return board.isInPalace(this.team, to);
    }

    private boolean isCorrectMoveDistance(Position from, Position to) {
        return Math.abs(from.columnDistanceTo(to)) == 1 || Math.abs(from.rowDistanceTo(to)) == 1;
    }

    private boolean isCorrectDirection(Position from, Position to, Board board) {
        // 1. 출발지가 정중앙인 경우, 궁성 내 어디든지 이동 가능
        if (board.isCenterPositionInPalace(team, from)) {
            return true;
        }

        // 2. 출발지가 정중앙이 아닌 경우, 중앙으로 이동 가능
        if (board.isCenterPositionInPalace(team, to)) {
            return true;
        }

        // 3. 출발지가 정중앙이 아닌 경우, 상하좌우로 이동 가능
        return !(Math.abs(from.columnDistanceTo(to)) == 1 && Math.abs(from.rowDistanceTo(to)) == 1);
    }

    private boolean isEmptySpace(Position to, Board board) {
        return board.isEmpty(to);
    }
}
