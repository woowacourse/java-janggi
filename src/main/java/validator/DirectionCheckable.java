package validator;

import position.Position;

import java.util.function.BiPredicate;

public interface DirectionCheckable {

    BiPredicate<Position, Position> directionRule();

    default void validateDirection(Position src, Position dest) {
        if (!directionRule().test(src, dest)) {
            throw new IllegalArgumentException("도달할 수 없는 방향입니다.");
        }
    }
}
