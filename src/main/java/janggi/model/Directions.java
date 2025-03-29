package janggi.model;

import java.util.ArrayList;
import java.util.List;

public class Directions {
    private final List<Direction> directions;

    public Directions(List<Direction> directions) {
        this.directions = directions;
    }

    public int calculateTotalDeltaRow() {
        return directions.stream().mapToInt(Direction::deltaRow).sum();
    }

    public int calculateTotalDeltaColumn() {
        return directions.stream().mapToInt(Direction::deltaColumn).sum();
    }

    public PositionsInDirection convertPositionsInDirections(Position startPosition) {
        validateMovableDestinationPosition(startPosition);
        List<Position> positions = new ArrayList<>();
        Position currentPosition = startPosition;
        for (Direction direction : directions) {
            currentPosition = currentPosition.move(direction);
            positions.add(currentPosition);
        }
        return new PositionsInDirection(positions);
    }

    private void validateMovableDestinationPosition(Position startPosition) {
        if (!startPosition.canMove(this)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
    }
}
