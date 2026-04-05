package domain.piece.strategy;

import domain.board.PathChecker;
import domain.board.Position;
import domain.piece.Piece;

import java.util.Collections;
import java.util.List;

public class ChariotStrategy implements MoveStrategy {

    private static final String CHARIOT_CANNOT_MOVE_ERROR_MESSAGE = "[ERROR] 차 이동 경로 상에 기물이 존재하여 움직일 수 없습니다.";
    private static final Position HAN_GENERAL = new Position(5, 2);
    private static final Position CHO_GENERAL = new Position(5, 9);

    @Override
    public void move(Position from, Position to, PathChecker checker) {
        List<Position> path = determinePath(from, to, checker);

        List<Piece> piecesInPath = checker.findPiecesInPath(path);

        if (!piecesInPath.isEmpty()) {
            throw new IllegalArgumentException(CHARIOT_CANNOT_MOVE_ERROR_MESSAGE);
        }
    }

    private List<Position> determinePath(Position from, Position to, PathChecker pathChecker) {
        if (isDiagonalMoveInPalace(from, to, pathChecker)) {
            return diagonalPath(from, to);
        }
        return from.findPath(to);
    }

    private boolean isDiagonalMoveInPalace(Position from, Position to, PathChecker pathChecker) {
        if (pathChecker.isInSamePalace(from, to)) {
            return false;
        }

        int dx = Math.abs(from.x() - to.x());
        int dy = Math.abs(from.y() - to.y());
        boolean isInPalace = pathChecker.isInPalace(from) && pathChecker.isInPalace(to);
        boolean isIncludePalaceCenter = pathChecker.isOnPalaceCenter(from) || pathChecker.isOnPalaceCenter(to);

        if (!(isInPalace && dx == dy)) {
            return false;
        }

        return dx == 2 || isIncludePalaceCenter;
    }

    private List<Position> diagonalPath(Position from, Position to) {
        if (Math.abs(from.x() - to.x()) == 1) {
            return Collections.emptyList();
        }
        return List.of(findPalaceCenter(from));
    }

    private Position findPalaceCenter(Position from) {
        if (from.y() < 4) {
            return HAN_GENERAL;
        }
        return CHO_GENERAL;
    }
}
