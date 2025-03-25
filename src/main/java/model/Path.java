package model;

import java.util.ArrayList;
import java.util.List;

public class Path {
    private final Position destinationPosition;
    private final List<Position> cornerPositions;

    public Path(Position startPosition, List<Direction> directions) {
        if (!startPosition.canMove(directions)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
        List<Position> positions = new ArrayList<>();
        Position currentPosition = startPosition;
        for (Direction direction : directions) {
            if (!currentPosition.canMove(direction)) {
                continue;
            }
            currentPosition = currentPosition.move(direction);
            positions.add(currentPosition);
        }
        destinationPosition = positions.getLast();
        positions.removeLast();
        cornerPositions = positions;
    }

    public Position getDestinationPosition() {
        return destinationPosition;
    }

    public List<Position> getCornerPositions() {
        return cornerPositions;
    }
}
