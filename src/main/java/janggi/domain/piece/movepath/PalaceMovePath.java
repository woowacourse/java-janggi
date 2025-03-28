package janggi.domain.piece.movepath;

import janggi.domain.piece.Direction;
import janggi.domain.piece.Palace;
import janggi.domain.piece.Point;
import java.util.List;

public class PalaceMovePath implements MovePath {

    private final Direction direction;

    public PalaceMovePath(Direction direction) {
        this.direction = direction;
    }

    @Override
    public boolean canMove(Point from, Point to) {
        if (!Palace.isInPalace(from, to)) {
            return false;
        }
        if (!from.move(direction).equals(to)) {
            return false;
        }
        if (direction.isDiagonal()) {
            return Palace.canMoveDiagonal(from, to);
        }
        return true;
    }

    @Override
    public List<Point> movePoints(Point from, Point to) {
        if (!canMove(from, to)) {
            throw new IllegalArgumentException("이동할 수 없습니다.");
        }
        return List.of(to);
    }
}
