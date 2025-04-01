package domain.direction;

import domain.spatial.Position;
import domain.spatial.Vector;
import java.util.ArrayList;
import java.util.List;

public class Direction {

    private final List<Vector> direction;

    public Direction(final List<Vector> direction) {
        this.direction = direction;
    }

    public boolean canReach(final Position start, final Position target, final boolean isRepeatable) {
        if (isRepeatable) {
            return canReachWithRepeat(start, target);
        }
        return canReachWithoutRepeat(start, target);
    }

    public List<Position> createPath(final Position start, final Position target, final boolean isRepeatable) {
        List<Position> paths = new ArrayList<>();
        paths.add(start);

        if (isRepeatable) {
            addPathWithRepetition(paths, start, target);
            return paths;
        }

        addPathWithoutRepetition(paths, start);
        return paths;
    }

    private boolean canReachWithRepeat(final Position start, final Position target) {
        Position current = start;
        Vector repeat = direction.getFirst();

        while (current.isMoveValid(repeat) && !current.equals(target)) {
            current = current.moveBy(repeat);
        }
        return current.equals(target);
    }

    private boolean canReachWithoutRepeat(final Position start, final Position target) {
        Position current = start;

        for (Vector move : direction) {
            if (!current.isMoveValid(move)) {
                break;
            }
            current = current.moveBy(move);
        }
        return current.equals(target);
    }

    private void addPathWithRepetition(final List<Position> paths, final Position start, final Position target) {
        Position current = start;
        while (!current.equals(target)) {
            current = current.moveBy(direction.getFirst());
            paths.add(current);
        }
    }

    private void addPathWithoutRepetition(final List<Position> paths, final Position start) {
        Position current = start;
        for (Vector vector : direction) {
            current = current.moveBy(vector);
            paths.add(current);
        }
    }
}
