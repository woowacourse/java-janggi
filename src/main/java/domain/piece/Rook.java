package domain.piece;

import domain.Board;
import domain.Palace;
import domain.PieceType;
import domain.Position;
import domain.Team;

public class Rook extends Piece{
    public Rook(Team team) {
        super(team, PieceType.ROOK);
    }

    @Override
    public boolean canMove(Position from, Position to, Board board) {
        // 1. 도착 지점이 같은 열 또는 행 or 궁성 내 간선 아닌 경우 이동 불가
        if (!isCorrectMoveDistanceAndDirection(from, to)) {
            return false;
        }

        // 2. 직선 이동 시 도착지에 같은 팀이 존재하는 경우 이동 불가
        if (board.hasSameTeamOn(to, this)) {
            return false;
        }

        // 3. 도착지랑 출발지 사이에 말이 하나라도 존재하면 이동 불가
        if (!hasOnePieceInPath(from, to, board)) {
            return false;
        }

        return true;
    }

    private boolean isCorrectMoveDistanceAndDirection(Position from, Position to) {
        if (Palace.isPalaceCorner(from)) {
            return Palace.isPalaceCenter(to) || Palace.isPalaceCorner(to) || from.isSameColumn(to) || from.isSameRow(to);
        }

        return from.isSameColumn(to) || from.isSameRow(to);
    }

    private boolean hasOnePieceInPath(Position from, Position to, Board board) {
        // 대각선 끝과 끝 이동시 궁성 중앙에 기물이 있는지 확인
        if (isDiagonalCornerToCorner(from, to)) {
            Position middlePosition = from.getMiddlePosition(to);
            // 길이 비어 있어야 이동 가능
            return board.isEmpty(middlePosition);
        }

        return board.findPiecesInLinePath(from, to).isEmpty();
    }

    // 대각선 끝과 끝 이동인지 확인
    private boolean isDiagonalCornerToCorner(Position from, Position to) {
        if (Math.abs(from.rowDistanceTo(to)) == 2 && Math.abs(from.columnDistanceTo(to)) == 2) {
            return true;
        }
        return false;
    }
}
