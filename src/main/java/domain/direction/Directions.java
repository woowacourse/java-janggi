package domain.direction;

import domain.spatial.Position;
import java.util.List;
import java.util.Objects;

public class Directions {

    private final List<Direction> directions;
    private final boolean repeatable;

    public Directions(final List<Direction> directions, final boolean repeatable) {
        this.directions = directions;
        this.repeatable = repeatable;
    }

    public List<Position> getPaths(final Position start, final Position target) {
        return directions.stream()
                .filter(element -> element.canReach(start, target, repeatable))
                .findFirst()
                .map(element -> element.createPath(start, target, repeatable))
                .orElse(List.of());
    }

    @Override
    public boolean equals(final Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Directions that = (Directions) object;
        return Objects.equals(directions, that.directions);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(directions);
    }
}
