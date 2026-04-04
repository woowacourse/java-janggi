package domain.board;

import domain.Direction;
import domain.Offset;

import java.util.List;

public class Palace {
    private static final Position CHO_PALACE_CENTER = new Position(4, 1);
    private static final Position HAN_PALACE_CENTER = new Position(4, 8);

    private Palace() {
    }

    public static boolean isInPalace(Position position) {
        return isInChoPalace(position) || isInHanPalace(position);
    }

    private static boolean isInChoPalace(Position position) {
        return position.x() >= CHO_PALACE_CENTER.x() - 1 &&
                position.x() <= CHO_PALACE_CENTER.x() + 1 &&
                position.y() >= CHO_PALACE_CENTER.y() - 1 &&
                position.y() <= CHO_PALACE_CENTER.y() + 1;
    }

    private static boolean isInHanPalace(Position position) {
        return position.x() >= HAN_PALACE_CENTER.x() - 1 &&
                position.x() <= HAN_PALACE_CENTER.x() + 1 &&
                position.y() >= HAN_PALACE_CENTER.y() - 1 &&
                position.y() <= HAN_PALACE_CENTER.y() + 1;
    }

    private static boolean isCenter(Position position) {
        if (position.equals(CHO_PALACE_CENTER)) {
            return true;
        }
        return position.equals(HAN_PALACE_CENTER);
    }

    private static boolean isConner(Position position) {
        boolean isChoCorner = getDiagonalPosition(CHO_PALACE_CENTER).contains(position);
        boolean isHanCorner = getDiagonalPosition(HAN_PALACE_CENTER).contains(position);

        return isChoCorner || isHanCorner;
    }

    private static List<Position> getDiagonalPosition(Position position) {
        List<Offset> cornerOffsets = List.of(
                new Offset(-1, -1), new Offset(-1, 1), new Offset(1, -1), new Offset(1, 1)
        );

        return cornerOffsets.stream()
                .map(offset -> offset.applyTo(position))
                .toList();
    }

    public static void validateDiagonalMoveRule(Position from, Position to) {
        validateInPalace(from, to);
        Offset offset = Offset.of(from, to);
        validateDiagonalMoving(offset);

        if (!(isConner(from) || isCenter(from))) {
            throw new IllegalStateException("연결된 경로가 존재하지 않습니다.");
        }
    }

    public static List<Offset> generatePaths(Position from, Position to) {
        validateDiagonalMoveRule(from, to);

        Offset offset = Offset.of(from, to);
        Direction diagonalDirection = offset.getDiagonalDirection();

        if (isConner(from) && isConner(to)) {
            return List.of(diagonalDirection.getOffset());
        }
        return List.of();
    }

    private static void validateDiagonalMoving(Offset offset) {
        if (!offset.isDiagonalMoving()) {
            throw new IllegalStateException("대각선 이동이 아닙니다.");
        }
    }

    private static void validateInPalace(Position from, Position to) {
        if (!(isInPalace(from) && isInPalace(to))) {
            throw new IllegalStateException("출발지 또는 목적지가 궁성이 아닙니다.");
        }
    }
}
