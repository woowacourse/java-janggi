package janggi.domain.piece.strategy;

import janggi.domain.Position;

import java.util.List;
import java.util.Optional;

public record Direction(int directionRow, int directionColumn) {

    public static List<Direction> linear() {
        return List.of(
                new Direction(1, 0),
                new Direction(-1, 0),
                new Direction(0, 1),
                new Direction(0, -1)
        );
    }

    public static Direction north() {
        return new Direction(1, 0);
    }

    public static Direction south() {
        return new Direction(-1, 0);
    }

    public static Direction west() {
        return new Direction(0, -1);
    }

    public static Direction east() {
        return new Direction(0, 1);
    }

    public static Direction northWest() {
        return new Direction(1, -1);
    }

    public static Direction northEast() {
        return new Direction(1, 1);
    }

    public static Direction southWest() {
        return new Direction(-1, -1);
    }

    public static Direction southEast() {
        return new Direction(-1, 1);
    }

    public static List<Direction> diagonalDirections() {
        return List.of(
                northWest(),
                northEast(),
                southWest(),
                southEast()
        );
    }

    public static List<Direction> orthogonalDirections() {
        return List.of(
                north(),
                south(),
                west(),
                east()
        );
    }

    public Optional<Position> findNextPosition(Position current) {
        return current.move(directionRow, directionColumn);
    }

    public boolean isSameDirectionOfProgress(Direction other) {
        return this.directionRow == other.directionRow;
    }
}
