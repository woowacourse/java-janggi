package domain.janggi.domain;

import java.util.ArrayList;
import java.util.List;

public class Path {
    private final List<Position> positions = new ArrayList<>();

    public Path(List<Direction> directions, Position position) {
        List<Position> pathPositions = new ArrayList<>();
        Position currPosition = position;
        for (Direction direction : directions) {
            currPosition = currPosition.moveByDirection(direction);
            pathPositions.add(currPosition);
        }
        this.positions.addAll(pathPositions);
    }

    public Position targetPosition() {
        return positions.getLast();
    }

    public List<Position> cornerPositions() {
        return positions.stream()
                .limit(positions.size() - 1)
                .toList();
    }
}
