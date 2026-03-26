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

        return bulidPath(src, dest, direction);
    }

    private boolean validateMove(Position src,Position dest) {
        if(src.equals(dest)) {
            return false;
        }

        return src.getX() == dest.getX() || src.getY() == dest.getY();
    }

    private Direction determineDirection(Position src, Position dest) {
        if(src.getX() == dest.getX()) {
            return getDirectionWhenXSame(src, dest);
        }

        if(src.getY() == dest.getY()) {
            return getDirectionWhenYSame(src, dest);
        }

        throw new IllegalArgumentException("갈 수 있는 경로가 없습니다.");
    }

    private Direction getDirectionWhenXSame(Position src, Position dest) {
        if(src.getY() > dest.getY()) {
            return Direction.SOUTH;
        }
        return Direction.NORTH;
    }

    private Direction getDirectionWhenYSame(Position src, Position dest) {
        if(src.getX() > dest.getX()) {
            return Direction.WEST;
        }
        return Direction.EAST;
    }

    private Path bulidPath(Position src, Position dest, Direction direction) {
        List<Position> path = new ArrayList<>();
        Position nextPosition = src;

        while (!nextPosition.equals(dest)) {
            nextPosition = direction.move(nextPosition);
            path.add(nextPosition);
        }

        return new Path(src, dest, path);
    }
}

