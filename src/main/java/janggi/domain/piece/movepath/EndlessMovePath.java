package janggi.domain.piece.movepath;

import janggi.domain.board.Direction;
import janggi.domain.board.Point;
import java.util.ArrayList;
import java.util.List;

public class EndlessMovePath implements MovePath {

    private final Direction direction;

    public EndlessMovePath(Direction direction) {
        this.direction = direction;
    }

    @Override
    public boolean canMove(Point from, Point to) {
        Point curr = from;
        while (!curr.equals(to) && curr.canMove(direction)) {
            curr = curr.move(direction);
        }
        return curr.equals(to);
    }

    @Override
    public List<Point> movePoints(Point from, Point to) {
        if (!canMove(from, to)) {
            throw new IllegalArgumentException("이동할 수 없습니다.");
        }
        return createMovePoints(from, to);
    }

    private List<Point> createMovePoints(Point from, Point to) {
        List<Point> points = new ArrayList<>();
        Point curr = from;
        while (!curr.isOutOfBoundary() && !curr.equals(to)) {
            curr = curr.move(direction);
            points.add(curr);
        }
        return points;
    }
}
