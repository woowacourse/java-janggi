package domain.piece;

import domain.JanggiCoordinate;

import java.util.function.Predicate;

public interface DistanceMove {
    default void validateReachableDistanceCoordinate(JanggiCoordinate from, JanggiCoordinate to, Predicate<Integer> available) {
        if (!available.test(from.distanceTo(to))) {
            throw new IllegalArgumentException("[ERROR] 기물이 해당 위치로 이동할 수 없습니다.");
        }
    }
}
