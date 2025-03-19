package domain.direction;

import domain.piece.Position;

import java.util.List;

public class Directions {

    private final List<Direction> directions;

    public Directions(final List<Direction> directions) {
        this.directions = directions;
    }

    public List<Position> getPath(final Position start, final Position target) {
        Direction direction = directions.stream()
                .filter(element -> element.canReach(start, target))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        return direction.createPath(start, target);
    }
}
