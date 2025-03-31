package janggi.domain.piece.movepath;

import janggi.domain.piece.Direction;
import janggi.domain.piece.Point;
import janggi.domain.piece.palace.Palace;
import java.util.List;

public class PalaceMovePath implements MovePath {

    private final Palace palace;
    private final Direction direction;

    public PalaceMovePath(Palace palace, Direction direction) {
        this.palace = palace;
        this.direction = direction;
    }

    @Override
    public boolean canMove(Point from, Point to) {
        if (!palace.canMoveInPalace(from, to, direction)) {
            return false;
        }

        return from.canMove(direction) && from.move(direction).equals(to);
    }

    @Override
    public List<Point> movePoints(Point from, Point to) {
        if (!canMove(from, to)) {
            throw new IllegalArgumentException("이동할 수 없습니다.");
        }
        return List.of(to);
    }
}
