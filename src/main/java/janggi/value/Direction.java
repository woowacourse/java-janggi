package janggi.value;

import java.util.List;

public enum Direction {
    ORIGIN(new RelativePosition(0, 0)),
    LEFT(new RelativePosition(-1, 0)),
    RIGHT(new RelativePosition(1, 0)),
    UP(new RelativePosition(0, -1)),
    DOWN(new RelativePosition(0, 1)),
    UP_LEFT(new RelativePosition(-1, -1)),
    DOWN_LEFT(new RelativePosition(-1, 1)),
    UP_RIGHT(new RelativePosition(1, -1)),
    DOWN_RIGHT(new RelativePosition(1, 1)),
    NONE(new RelativePosition(0, 0));

    private final RelativePosition relativePosition;

    Direction(RelativePosition relativePosition) {
        this.relativePosition = relativePosition;
    }

    public static Direction parse(Position start, Position end) {
        RelativePosition relativePosition = new RelativePosition(start, end);
        RelativePosition unitPosition = relativePosition.calculateUnit();
        List<Direction> directions = List.of(Direction.values());
        return directions.stream()
                .filter(direction -> direction.getRelativePosition().equals(unitPosition))
                .findFirst()
                .orElse(NONE);
    }

    public List<Position> calculatePositionInDirection(Position start, Position end) {
        if (this == Direction.LEFT || this == Direction.RIGHT) {
            return start.makeInXLine(end);
        } else if (this == Direction.UP || this == Direction.DOWN) {
            return start.makeInYLine(end);
        } else if (this == Direction.UP_LEFT || this == Direction.DOWN_RIGHT) {
            return start.makeInDiagonalWithPlusOneSlop(end);
        } else if (this == Direction.UP_RIGHT || this == Direction.DOWN_LEFT) {
            return start.makeInDiagonalWithMinusOneSlop(end);
        }
        return List.of();
    }

    public RelativePosition getRelativePosition() {
        return relativePosition;
    }
}
