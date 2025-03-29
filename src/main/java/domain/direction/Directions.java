package domain.direction;

import domain.piece.Palace;
import domain.piece.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class Directions {

    private final Set<Direction> directions;
    private final boolean repeatable;

    public Directions(final Set<Direction> directions, boolean repeatable) {
        this.directions = directions;
        this.repeatable = repeatable;
    }

    public List<Position> getPath(final Position start, final Position target) {
        Optional<Direction> direction = directions.stream()
                .filter(element -> element.canReach(start, target, repeatable))
                .findFirst();
        return direction.map(value -> value.createPath(start, target, repeatable)).orElse(new ArrayList<>());
    }

    public List<Position> getPalacePath(final Position start, final Position target) {
        Optional<Direction> direction = Palace.getMovableDirectionInPalace(start).stream()
                .filter(element -> element.canReach(start, target, repeatable))
                .findFirst();

        return direction.map(value -> value.createPath(start, target, repeatable)).orElse(new ArrayList<>());
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
