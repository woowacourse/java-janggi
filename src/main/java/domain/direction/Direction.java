package domain.direction;

import domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Direction {

    private final List<Vector> direction;

    public Direction(final List<Vector> direction) {
        this.direction = direction;
    }

    public boolean canReach(final Position start, final Position target, final boolean repeatable) {
        if (repeatable) {
            return canReachWithRepeat(start, target);
        }
        return canReachWithoutRepeat(start, target);
    }

    public List<Position> createPath(final Position start, final Position target, final boolean repeatable) {
        if (repeatable) {
            return createPathWithRepeat(start, target);
        }
        return createPathWithoutRepeat(start);
    }

    private boolean canReachWithRepeat(final Position start, final Position target) {
        Position result = start;
        while (result.isValid() && !result.equals(target)) {
            result = result.merge(direction.getFirst());
        }
        return result.isValid() && result.equals(target);
    }

    private boolean canReachWithoutRepeat(final Position start, final Position target) {
        Position result = start;
        for (Vector vector : direction) {
            result = result.merge(vector);
        }
        return result.equals(target);
    }

    private List<Position> createPathWithRepeat(final Position start, final Position target) {
        List<Position> paths = new ArrayList<>();

        Position path = start;
        while (!path.equals(target)) {
            path = path.merge(direction.getFirst());
            paths.add(path);
        }
        return paths.subList(0, paths.size() - 1);
    }

    private List<Position> createPathWithoutRepeat(final Position start) {
        List<Position> paths = new ArrayList<>();

        Position path = start;
        for (Vector vector : direction) {
            paths.add(path);
            path = path.merge(vector);
        }
        return paths.subList(1, paths.size());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Direction direction1 = (Direction) object;
        return Objects.equals(direction, direction1.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(direction);
    }
}
