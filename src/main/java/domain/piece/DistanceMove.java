package domain.piece;

import domain.Direction;
import domain.JanggiCoordinate;

import java.util.function.Predicate;

public interface DistanceMove {

    default Direction getDirection(JanggiCoordinate from, JanggiCoordinate to, int distance) {
        if (from.row() + distance == to.row()) {
            return Direction.DOWN;
        }
        if (from.row() - distance == to.row()) {
            return Direction.UP;
        }
        if (from.col() + distance == to.col()) {
            return Direction.RIGHT;
        }
        return Direction.LEFT;
    }

    default Direction getDiagonalDirection(JanggiCoordinate from, JanggiCoordinate to) {
        if (from.row() > to.row() && from.col() > to.col()) {
            return Direction.RIGHT_DOWN;
        }
        if (from.row() > to.row() && from.col() < to.col()) {
            return Direction.LEFT_DOWN;
        }
        if (from.row() < to.row() && from.col() > to.col()) {
            return Direction.LEFT_UP;
        }
        return Direction.RIGHT_UP;
    }

    default void validateReachableDistanceCoordinate(JanggiCoordinate from, JanggiCoordinate to, Predicate<Integer> available) {
        if (!available.test(from.distanceTo(to))) {
            throw new IllegalArgumentException("[ERROR] 기물이 해당 위치로 이동할 수 없습니다.");
        }
    }
}
