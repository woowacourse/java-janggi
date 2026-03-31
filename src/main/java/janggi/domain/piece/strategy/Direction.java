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

    public static Direction up() {
        return new Direction(1, 0);
    }

    public static Direction down() {
        return new Direction(-1, 0);
    }

    public static Direction left() {
        return new Direction(0, -1);
    }

    public static Direction right() {
        return new Direction(0, 1);
    }

    public Optional<Position> findNextPosition(Position current) {
        return current.move(directionRow, directionColumn);
    }
}
