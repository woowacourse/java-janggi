package janggi.direction;

import janggi.value.Position;
import java.util.List;

public enum OneStepDirection {
    LEFT(new Position(-1, 0)),
    RIGHT(new Position(1, 0)),
    UP(new Position(0, -1)),
    DOWN(new Position(0, 1)),
    NONE(new Position(0, 0));

    private final Position relativePosition;

    OneStepDirection(Position relativePosition) {
        this.relativePosition = relativePosition;
    }

    public static OneStepDirection parse(Position current, Position destination) {
        Position difference = current.calculateDifference(destination);
        Position differenceWithMinusY = new Position(difference.x(), -difference.y());
        List<OneStepDirection> allDirection = List.of(OneStepDirection.values());
        return allDirection.stream()
                .filter(direction -> direction.getRelativePosition().equals(differenceWithMinusY))
                .findFirst()
                .orElse(OneStepDirection.NONE);
    }

    public Position getRelativePosition() {
        return relativePosition;
    }
}
