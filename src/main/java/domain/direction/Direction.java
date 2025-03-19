package domain.direction;

import domain.piece.Position;
import java.util.ArrayList;
import java.util.List;

public class Direction {

    private final List<Position> direction;
    private final boolean repeatable;

    public Direction(final List<Position> direction, final boolean repeatable) {
        this.direction = direction;
        this.repeatable = repeatable;
    }

    public boolean canReach(final Position start, final Position target) {
        if (repeatable) {
            return canReachWithRepeat(start, target);
        }
        return canReachWithoutRepeat(start, target);
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
        for (Position dir : direction) {
            result = result.merge(dir);
        }
        return result.equals(target);
    }

    public List<Position> createPath(final Position start, final Position target) {
        if (repeatable) {
            return createPathWithRepeat(start, target);
        }
        return createPathWithoutRepeat(start);
    }

    private List<Position> createPathWithRepeat(final Position start, final Position target) {
        List<Position> paths = new ArrayList<>();

        Position path = start;
        while (!path.equals(target)) {
            path = path.merge(direction.getFirst());
            paths.add(path);
        }
        return paths;
    }

    private List<Position> createPathWithoutRepeat(final Position start) {
        List<Position> paths = new ArrayList<>();

        Position path = start;
        for (Position dir : direction) {
            paths.add(path);
            path = path.merge(dir);
        }
        return paths.subList(1, direction.size());
    }
}
