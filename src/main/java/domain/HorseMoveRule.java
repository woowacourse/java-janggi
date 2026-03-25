package domain;

import java.util.List;
import java.util.function.Function;

public enum HorseMoveRule {

    UP_CROSS_RIGHT(position -> position.up().upCrossRight(), position -> List.of(position.up())),
    UP_CROSS_LEFT(position -> position.up().upCrossLeft(), position -> List.of(position.up())),
    ;

    private final Function<Position, Position> destination;
    private final Function<Position, List<Position>> route;

    HorseMoveRule(Function<Position, Position> destination, Function<Position, List<Position>> route) {
        this.destination = destination;
        this.route = route;
    }

    public boolean isContainMoveable(Position currentPosition, Position targetPosition) {
        for (HorseMoveRule value : HorseMoveRule.values()) {
            if (value.destination.apply(currentPosition) == targetPosition) {
                return true;
            }
        }

        return false;
    }
}
