package domain.rule;

import domain.direction.Direction;
import domain.position.Path;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class StraightPathGenerator implements PathGenerator {

    @Override
    public Path calculatePath(Position src, Position dest) {
        if(!validateMove(src, dest)) {
            throw new IllegalArgumentException("이동 할 수 있는 경로가 아닙니다.");
        }

        Direction direction = determineDirection(src, dest);

        return buildPath(src, dest, direction);
    }

    private boolean validateMove(Position src,Position dest) {
        if(src.equals(dest)) {
            return false;
        }

        return src.x() == dest.x() || src.y() == dest.y();
    }

    private Direction determineDirection(Position src, Position dest) {
        if(src.x() == dest.x()) {
            return getDirectionWhenXSame(src, dest);
        }

        if(src.y() == dest.y()) {
            return getDirectionWhenYSame(src, dest);
        }

        throw new IllegalArgumentException("갈 수 있는 경로가 없습니다.");
    }

    private Direction getDirectionWhenXSame(Position src, Position dest) {
        if(src.y() > dest.y()) {
            return Direction.SOUTH;
        }
        return Direction.NORTH;
    }

    private Direction getDirectionWhenYSame(Position src, Position dest) {
        if(src.x() > dest.x()) {
            return Direction.WEST;
        }
        return Direction.EAST;
    }

    private Path buildPath(Position src, Position dest, Direction direction) {
        List<Position> path = new ArrayList<>();
        Position current = src;

        while (!current.equals(dest)) {
            current = direction.move(current);
            path.add(current);
        }

        path.removeLast();
        return new Path(src, dest, path);
    }
}

