package domain.direction;

import domain.piece.Position;
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
        // TODO : 궁성의 경우, 대각선 Direction이 포함 안되어 있음
        // 궁성으로 이동할 수 있는데 orElseThrow에서 터지면?
        Optional<Direction> direction = directions.stream()
                .filter(element -> element.canReach(start, target, repeatable))
                .findFirst();

        return direction.map(value -> value.createPath(start, target, repeatable)).orElse(null);
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
