package janggi.domain.piece.strategy;

import janggi.domain.Position;
import java.util.List;
import java.util.Optional;

public record Direction(int dr, int dc) {

    public static List<Direction> linear() {
        return List.of(
                new Direction(1, 0),
                new Direction(-1, 0),
                new Direction(0, 1),
                new Direction(0, -1)
        );
    }

    public Optional<Position> next(Position current) {
        return current.move(dr, dc);
    }
}