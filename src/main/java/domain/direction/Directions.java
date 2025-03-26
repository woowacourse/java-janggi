package domain.direction;

import domain.spatial.Position;
import java.util.List;
import java.util.Objects;

public class Directions {

    private final List<Direction> directions;

    public Directions(final List<Direction> directions) {
        this.directions = directions;
    }

    public List<Position> getPaths(final Position start, final Position target) {
        Direction direction = directions.stream()
                .filter(element -> element.canReach(start, target))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이동할 수 없는 좌표입니다. 다시 확인해주세요."));

        return direction.createPath(start, target);
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
