package domain.piece;

import domain.Direction;
import domain.JanggiBoard;
import domain.JanggiCoordinate;

public interface LinearMove {
    void validateLinearMove(JanggiBoard janggiBoard, JanggiCoordinate from, JanggiCoordinate to);

    void validateReachAble(JanggiBoard janggiBoard, JanggiCoordinate from, JanggiCoordinate to);

    default Direction getDirection(JanggiCoordinate from, JanggiCoordinate to) {
        if (isSameRow(from, to)) {
            return getHorizontalDirection(from, to);
        }
        return getVerticalDirection(from, to);
    }

    default Direction getVerticalDirection(JanggiCoordinate from, JanggiCoordinate to) {
        if (from.row() > to.row()) {
            return Direction.UP;
        }
        return Direction.DOWN;
    }

    default Direction getHorizontalDirection(JanggiCoordinate from, JanggiCoordinate to) {
        if (from.col() > to.col()) {
            return Direction.LEFT;
        }
        return Direction.RIGHT;
    }

    default void validateRowCol(JanggiCoordinate from, JanggiCoordinate to) {
        if (!isSameRow(from, to) && !isSameCol(from, to)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치로 이동할 수 없습니다.");
        }
    }

    default boolean isSameRow(JanggiCoordinate from, JanggiCoordinate to) {
        return from.row() == to.row();
    }

    default boolean isSameCol(JanggiCoordinate from, JanggiCoordinate to) {
        return from.col() == to.col();
    }
}
