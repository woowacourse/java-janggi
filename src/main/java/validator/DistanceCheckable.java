package validator;

import position.Position;

public interface DistanceCheckable {

    double getExpectedDistance();

    default void validateDistance(Position src, Position dest) {
        double actual = src.calculateDistance(dest);
        if (Math.abs(actual - getExpectedDistance()) > 1e-9) {
            throw new IllegalArgumentException("거리가 맞지 않습니다.");
        }
    }
}
