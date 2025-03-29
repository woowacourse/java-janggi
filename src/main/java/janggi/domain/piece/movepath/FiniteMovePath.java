package janggi.domain.piece.movepath;

import janggi.domain.piece.Direction;
import janggi.domain.piece.Point;
import java.util.ArrayList;
import java.util.List;

public class FiniteMovePath implements MovePath {
    private final List<Direction> directions;

    public FiniteMovePath(Direction... directions) {
        this(List.of(directions));
    }

    public FiniteMovePath(List<Direction> directions) {
        this.directions = new ArrayList<>(directions);
    }

    @Override
    public boolean canMove(Point from, Point to) {
        Point curr = from;
        for (Direction direction : directions) {
            if (!curr.canMove(direction)) {
                return false;
            }
            curr = curr.move(direction);
        }
        return curr.equals(to);
    }

    @Override
    public List<Point> movePoints(Point from, Point to) {
        if (!canMove(from, to)) {
            throw new IllegalArgumentException("이동할 수 없습니다.");
        }
        return createMovePoints(from);
    }

    private List<Point> createMovePoints(Point from) {
        List<Point> points = new ArrayList<>();
        Point curr = from;
        for (Direction direction : directions) {
            curr = curr.move(direction);
            points.add(curr);
        }
        return points;
    }
}
