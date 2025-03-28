package janggi.domain.piece.movepath;

import janggi.domain.piece.Direction;
import janggi.domain.piece.Palace;
import janggi.domain.piece.Point;
import java.util.ArrayList;
import java.util.List;

public class EndlessPalaceMovePath implements MovePath {

    private final Direction direction;

    public EndlessPalaceMovePath(Direction direction) {
        this.direction = direction;
    }

    @Override
    public boolean canMove(Point from, Point to) {
        if (!Palace.canMoveInPalace(from, to, direction)) {
            return false;
        }

        Point current = from;
        while (Palace.isInPalace(current) && !current.equals(to)) {
            current = current.move(direction);
        }
        return current.equals(to);
    }

    @Override
    public List<Point> movePoints(Point from, Point to) {
        if (!canMove(from, to)) {
            throw new IllegalArgumentException("이동할 수 없습니다.");
        }

        Point current = from;
        List<Point> movePath = new ArrayList<>();
        while (Palace.isInPalace(current) && !current.equals(to)) {
            current = current.move(direction);
            movePath.add(current);
        }
        return movePath;
    }
}
