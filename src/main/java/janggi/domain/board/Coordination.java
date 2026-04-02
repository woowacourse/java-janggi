package janggi.domain.board;

import janggi.domain.board.point.Point;

public interface Coordination {
    public boolean isInRange(int nx, int ny);

    default void validateRange(Point point) {
        if (!isInRange(point.x(), point.y())) {
            throw new IllegalStateException("유효하지 않은 범위입니다.");
        }
    }
}
