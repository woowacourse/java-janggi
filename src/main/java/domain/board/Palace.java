package domain.board;

import domain.Direction;
import domain.Offset;

import java.util.Arrays;
import java.util.List;

public enum Palace {
    CHO(4, 1),
    HAN(4, 8);

    private final Position center;

    Palace(int x, int y) {
        this.center = new Position(x, y);
    }

    public static boolean isInAnyPalace(Position position) {
        return Arrays.stream(values())
                .anyMatch(palace -> palace.isInPalace(position));
    }

    public static Palace findPalace(Position position) {
        return Arrays.stream(values())
                .filter(palace -> palace.isInPalace(position))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("궁성이 아닙니다."));
    }

    public boolean isInPalace(Position position) {
        return position.x() >= center.x() - 1 &&
                position.x() <= center.x() + 1 &&
                position.y() >= center.y() - 1 &&
                position.y() <= center.y() + 1;
    }

    public void validateDiagonalMoveRule(Position from, Position to) {
        validateInPalace(from, to);
        Offset offset = Offset.of(from, to);
        validateDiagonalMoving(offset);

        if (!(isCorner(from) || isCenter(from))) {
            throw new IllegalStateException("연결된 경로가 존재하지 않습니다.");
        }
    }

    public List<Offset> generatePaths(Position from, Position to) {
        validateDiagonalMoveRule(from, to);

        Offset offset = Offset.of(from, to);
        Direction diagonalDirection = offset.getDiagonalDirection();

        if (isCorner(from) && isCorner(to)) {
            return List.of(diagonalDirection.getOffset());
        }
        return List.of();
    }

    private boolean isCenter(Position position) {
        return center.equals(position);
    }

    private boolean isCorner(Position position) {
        List<Offset> cornerOffsets = List.of(
                new Offset(-1, -1), new Offset(-1, 1), new Offset(1, -1), new Offset(1, 1)
        );
        return cornerOffsets.stream()
                .map(offset -> offset.applyTo(center))
                .anyMatch(position::equals);
    }

    private void validateInPalace(Position from, Position to) {
        if (!(isInPalace(from) && isInPalace(to))) {
            throw new IllegalStateException("출발지 또는 목적지가 같은 궁성이 아닙니다.");
        }
    }

    private static void validateDiagonalMoving(Offset offset) {
        if (!offset.isDiagonalMoving()) {
            throw new IllegalStateException("대각선 이동이 아닙니다.");
        }
    }
}
