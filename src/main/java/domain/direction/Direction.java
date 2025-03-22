package domain.direction;

import domain.spatial.Position;
import domain.spatial.Vector;
import java.util.ArrayList;
import java.util.List;

public class Direction {

    private final List<Vector> direction;
    private final boolean repeatable;

    public Direction(final List<Vector> direction, final boolean repeatable) {
        this.direction = direction;
        this.repeatable = repeatable;
    }

    public boolean canReach(final Position start, final Position target) {
        if (repeatable) {
            return canReachWithRepeat(start, target);
        }
        return canReachWithoutRepeat(start, target);
    }

    public List<Position> createPath(final Position start, final Position target) {
        if (repeatable) {
            return createPathWithRepeat(start, target);
        }
        return createPathWithoutRepeat(start);
    }

    private boolean canReachWithRepeat(final Position start, final Position target) {
        Position current = start;
        Vector vector = direction.getFirst();
        while (current.isMoveValid(vector) && !current.equals(target)) {
            current = current.moveBy(vector);
        }
        return current.equals(target);
    }

    private boolean canReachWithoutRepeat(final Position start, final Position target) {
        Position current = start;
        for (Vector vector : direction) {
            current = current.moveBy(vector);
        }
        return current.equals(target);
    }

    private List<Position> createPathWithRepeat(final Position start, final Position target) {
        List<Position> paths = new ArrayList<>();

        Position path = start;
        while (!path.equals(target)) {
            path = path.moveBy(direction.getFirst());
            paths.add(path);
        }
        return paths.subList(0, paths.size() - 1);
    }

    private List<Position> createPathWithoutRepeat(final Position start) {
        List<Position> paths = new ArrayList<>();

        Position path = start;
        for (Vector vector : direction) {
            paths.add(path);
            path = path.moveBy(vector);
        }
        return paths.subList(1, paths.size());
    }
}
