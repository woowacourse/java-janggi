package domain;

import domain.strategy.Direction;

import java.util.List;

public enum PalacePosition {
    LEFT_TOP(new Position(0, 0), List.of(Direction.EAST, Direction.SOUTH, Direction.SOUTH_EAST)),
    RIGHT_TOP(new Position(0, 2), List.of(Direction.WEST, Direction.SOUTH, Direction.SOUTH_WEST)),
    LEFT_BOTTOM(new Position(2, 0), List.of(Direction.NORTH, Direction.EAST, Direction.NORTH_EAST)),
    RIGHT_BOTTOM(new Position(2, 2), List.of(Direction.NORTH, Direction.WEST, Direction.NORTH_WEST)),
    MIDDLE_TOP(new Position(0, 1), List.of(Direction.EAST, Direction.WEST, Direction.SOUTH)),
    LEFT_MIDDLE(new Position(1, 0), List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST)),
    RIGHT_MIDDLE(new Position(1, 2), List.of(Direction.NORTH, Direction.SOUTH, Direction.WEST)),
    MIDDLE_BOTTOM(new Position(2,1), List.of(Direction.EAST, Direction.WEST, Direction.NORTH)),
    CENTER(new Position(1,1), List.of(Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH,
            Direction.NORTH_EAST, Direction.NORTH_WEST, Direction.SOUTH_EAST, Direction.SOUTH_WEST)),;

    private final Position position;
    private final List<Direction> directions;

    PalacePosition(Position position, List<Direction> directions) {
        this.position = position;
        this.directions = directions;
    }

    public Position getPosition() {
        return position;
    }

    public List<Direction> getDirections() {
        return directions;
    }

    public static PalacePosition findByPosition(Position position) {
        for (PalacePosition palacePosition : PalacePosition.values()) {
            if (position.isSamePosition(palacePosition.getPosition())) {
                return palacePosition;
            }
        }
        throw new IllegalArgumentException("[ERROR] 해당하는 궁성 포지션을 찾을 수 없습니다.");
    }
}
