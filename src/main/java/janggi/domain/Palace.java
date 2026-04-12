package janggi.domain;

import janggi.domain.position.Direction;
import janggi.domain.position.Position;

import java.util.List;
import java.util.Set;

public class Palace {
    private static final Palace CHO = new Palace(
            Position.of(1, 4),
            Set.of(
                    Position.of(0, 3), Position.of(0, 4), Position.of(0, 5),
                    Position.of(1, 3), Position.of(1, 4), Position.of(1, 5),
                    Position.of(2, 3), Position.of(2, 4), Position.of(2, 5)
            ));

    private static final Palace HAN = new Palace(Position.of(8, 4),
            Set.of(
                    Position.of(7, 3), Position.of(7, 4), Position.of(7, 5),
                    Position.of(8, 3), Position.of(8, 4), Position.of(8, 5),
                    Position.of(9, 3), Position.of(9, 4), Position.of(9, 5)
            ));

    private final Position center;
    private final Set<Position> area;

    private Palace(Position center, Set<Position> area) {
        this.center = center;
        this.area = area;
    }

    public static Palace cho() {
        return CHO;
    }

    public static Palace han() {
        return HAN;
    }

    public boolean contains(Position position) {
        return area.contains(position);
    }

    public List<Direction> diagonalDirectionsAt(Position position) {
        if (!contains(position)) {
            return List.of();
        }

        if (position.equals(center)) {
            return Direction.diagonal();
        }

        return Direction.diagonal().stream()
                .filter(dir -> position.move(dir)
                        .map(next -> next.equals(center))
                        .orElse(false))
                .toList();
    }
}
