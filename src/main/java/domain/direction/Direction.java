package domain.direction;

import domain.piece.Position;

import java.util.ArrayList;
import java.util.List;

public class Direction {

    private final List<Position> direction;

    public Direction(List<Position> direction) {
        this.direction = direction;
    }

    public boolean canReach(Position start, Position target) {
        Position result = start;
        for (Position dir : direction) {
            result = result.merge(dir);
        }
        return result.equals(target);
    }

    public List<Position> createPath(final Position start) {
        List<Position> paths = new ArrayList<>();

        Position path = start;
        for (Position dir : direction) {
            paths.add(path);
            path = path.merge(dir);
        }
        return paths.subList(1, direction.size());
    }
}
