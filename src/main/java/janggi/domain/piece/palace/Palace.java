package janggi.domain.piece.palace;

import janggi.domain.piece.Direction;
import janggi.domain.piece.Dynasty;
import janggi.domain.piece.Point;
import java.util.List;

public abstract class Palace {

    public static Palace from(Dynasty dynasty) {
        if (dynasty == Dynasty.HAN) {
            return new HanPalace();
        }
        if (dynasty == Dynasty.CHU) {
            return new ChuPalace();
        }
        throw new IllegalArgumentException(dynasty + "에 해당하는 궁성은 존재하지 않습니다.");
    }

    public boolean canMoveInPalace(Point from, Point to, Direction direction) {
        if (!isInPalace(from) || !isInPalace(to)) {
            return false;
        }

        if (direction.isDiagonal()) {
            return Direction.isDiagonal(from, to) && isDiagonalPoint(from) && isDiagonalPoint(to);
        }

        return true;
    }

    public boolean isInPalace(Point point) {
        return palacePoints().stream()
                .anyMatch(each -> each.equals(point));
    }

    private boolean isDiagonalPoint(Point point) {
        return diagonalPoints().stream()
                .anyMatch(each -> each.equals(point));
    }

    abstract protected List<Point> palacePoints();

    abstract protected List<Point> diagonalPoints();
}
