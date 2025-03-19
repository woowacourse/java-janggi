package domain.direction;

import domain.piece.Position;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(final Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Directions that = (Directions) object;
        return Objects.equals(directions, that.directions);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(directions);
    }
}
