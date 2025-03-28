package janggi.domain.piece;

import java.util.List;
import java.util.Set;

public record Position(int x, int y) {

    private static final List<Set<Position>> palaceDiagonals = List.of(
            Set.of(
                    new Position(8, 4), new Position(9, 5), new Position(10, 6)
            ),
            Set.of(
                    new Position(8, 6), new Position(9, 5), new Position(10, 4)
            ),
            Set.of(
                    new Position(1, 4), new Position(2, 5), new Position(3, 6)
            ),
            Set.of(
                    new Position(1, 6), new Position(2, 5), new Position(3, 4)
            )
    );

    public Position {
        validatePosition(x, y);
    }

    public Position plus(Position other) {
        return new Position(other.x() + x, other.y() + y);
    }

    public Position plus(int x, int y) {
        return new Position(this.x + x, this.y + y);
    }

    public boolean isPalace() {
        return (x >= 8 && x <= 10 || x >= 1 && x <= 3) &&
                y >= 4 && y <= 6;
    }

    public boolean isNotPalace() {
        return !isPalace();
    }

    public boolean isEndPoint() {
        return x == 1 || x == 10 || y == 1 || y == 9;
    }

    public boolean isNotEndPoint() {
        return !isEndPoint();
    }

    public boolean isInSameDiagonalInPalace(Position positionToMove) {
        return isPalace() && palaceDiagonals.stream()
                .anyMatch(diagonal ->
                        diagonal.contains(this) && diagonal.contains(positionToMove)
                );
    }

    private void validatePosition(int x, int y) {
        if (x <= 0 || x > 10 || y <= 0 || y > 9) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다");
        }
    }
}
