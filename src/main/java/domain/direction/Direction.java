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

    public List<Position> createPath(final Position start, final Position target) {
        if (repeatable) {
            return createPathWithRepeat(start, target);
        }
        return createPathWithoutRepeat(start);
    }

    private boolean canReachWithRepeat(final Position start, final Position target) {
        Position current = start;
        while (current.isValid() && !current.equals(target)) {
            current = current.merge(direction.getFirst());
        }
        return current.isValid() && current.equals(target);
    }

    private boolean canReachWithoutRepeat(final Position start, final Position target) {
        Position current = start;
        for (Position dir : direction) {
            current = current.merge(dir);
        }
        return current.equals(target);
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
        for (Position dir : direction) {
            paths.add(path);
            path = path.merge(dir);
        }
        return paths.subList(1, paths.size());
    }
}
