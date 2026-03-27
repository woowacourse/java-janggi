package domain.piece;

import domain.Direction;
import domain.Path;
import domain.board.Position;

import java.util.ArrayList;
import java.util.List;

public class CannonStrategy implements MoveStrategy {
    @Override
    public List<Position> getPathPositions(Position from, Position to) {
        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();

        if (!((dx == 0 && dy != 0) || (dx != 0 && dy == 0))){
            throw new IllegalArgumentException("차를 해당 위치로 옮길 수 없습니다.");
        }

        Direction mainDirection;
        int distance;
        if (dx == 0) {
            mainDirection = decideYDirection(dy);
            distance = Math.abs(dy);
        } else {
            mainDirection = decideXDirection(dx);
            distance = Math.abs(dx);
        }

        Position step = from;

        List<Position> route = new ArrayList<>();
        for (int i = 0; i < distance; i++) {
            step = step.next(mainDirection);
            route.add(step);
        }

        return route;
    }

    private Direction decideXDirection(int dx) {
        if (dx > 0) {
            return Direction.RIGHT;
        }
        return Direction.LEFT;
    }

    private Direction decideYDirection(int dy) {
        if (dy > 0) {
            return Direction.UP;
        }
        return Direction.DOWN;
    }


    @Override
    public void canMove(List<Path> paths, Position to) {


    }
}
