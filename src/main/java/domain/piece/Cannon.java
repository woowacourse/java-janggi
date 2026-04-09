package domain.piece;

import domain.Board;
import domain.Palace;
import domain.PieceType;
import domain.Position;
import domain.Team;
import java.util.List;

/**
 * 1. 도착 지점이 같은 열이 아닌 경우 이동 불가 2. 도착지에 같은 팀이 존재하는 경우 이동 불가 3. 도착지랑 출발지 사이에 오직 하나의 말이 존재하지 않는 경우 이동 불가 4. 도착지와 출발지 사이의 말
 * 하나가 포인 경우 이동 불가 5. 도착지에 상대팀 말이 존재하면서, 해당 말이 포인 경우 이동 불가 6. 이외는 이동 가능
 */
public class Cannon extends Piece {
    public Cannon(Team team) {
        super(team, PieceType.CANNON);
    }

    @Override
    public boolean canMove(Position from, Position to, Board board) {
        // 1. 도착 지점이 같은 열 또는 행이 아닌 경우 이동 불가
        if (!isCorrectMoveDistanceAndDirection(from, to)) {
            return false;
        }

        // 2. 도착지에 같은 팀이 존재하는 경우 이동 불가
        if (board.hasSameTeamOn(to, this)) {
            return false;
        }

        // 3. 도착지랑 출발지 사이에 오직 하나의 말이 존재하지 않는 경우 이동 불가
        if (!hasOnePieceInPath(from, to, board)) {
            return false;
        }

        // 4. 도착지와 출발지 사이의 말 하나가 포인 경우 이동 불가
        if (hasCannonTypeInList(from, to, board) || middlePositionTypeIsCannon(from, to, board)) {
            return false;
        }

        // 5. 도착지에 상대팀 말이 존재하면서, 해당 말이 포인 경우 이동 불가
        if (!isEmptySpace(to, board) && isExistCannon(to, board)) {
            return false;
        }

        return true;
    }

    private boolean isEmptySpace(Position to, Board board) {
        return board.isEmpty(to);
    }

    private boolean isExistCannon(Position to, Board board) {
        return board.isExistSameType(to, this);
    }

    private boolean isCorrectMoveDistanceAndDirection(Position from, Position to) {
        if (Palace.isPalaceCorner(from)) {
            return Palace.isPalaceCorner(to) || from.isSameColumn(to) || from.isSameRow(to);
        }

        return from.isSameColumn(to) || from.isSameRow(to);
    }

    private boolean hasOnePieceInPath(Position from, Position to, Board board) {
        // 대각선 끝과 끝 이동시 궁성 중앙에 기물이 있는지 확인
        if (isDiagonalCornerToCorner(from, to)) {
            Position middlePosition = from.getMiddlePosition(to);
            // 길이 비어 있어야 이동 가능
            return !board.isEmpty(middlePosition);
        }

        if (board.findPiecesInLinePath(from, to).size() == 1) {
            return true;
        }
        return false;
    }

    private boolean isDiagonalCornerToCorner(Position from, Position to) {
        if (Math.abs(from.rowDistanceTo(to)) == 2 && Math.abs(from.columnDistanceTo(to)) == 2) {
            return true;
        }
        return false;
    }



    private boolean hasCannonTypeInList(Position from, Position to, Board board) {
        List<Piece> result = board.findPiecesInLinePath(from, to);
        return result.stream()
                .anyMatch(piece -> piece.isSameType(PieceType.CANNON));
    }

    private boolean middlePositionTypeIsCannon(Position from, Position to, Board board) {
        if (isDiagonalCornerToCorner(from, to)) {
            Position middlePosition = from.getMiddlePosition(to);
            return board.isExistSameType(middlePosition, this);
        }
        return false;
    }
}
