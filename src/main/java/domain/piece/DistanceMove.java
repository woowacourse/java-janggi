package domain.piece;

import domain.JanggiCoordinate;

public interface DistanceMove {
    default void validateReachableCoordinate(JanggiCoordinate from, JanggiCoordinate to, int distance) {
        if (from.distanceTo(to) != distance) {
            throw new IllegalArgumentException("[ERROR] 마가 해당 위치로 이동할 수 없습니다.");
        }
    }
}
