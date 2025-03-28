package janggi.model;

import java.util.List;
import java.util.Optional;

public class PositionsInDirection {
    private final List<Position> positions;

    public PositionsInDirection(List<Position> positions) {
        this.positions = positions;
    }

    public Path getPathUntilHuddle(OccupiedPositions occupied) {
        Optional<Position> firstHuddle = findFirstHuddle(occupied);
        if (firstHuddle.isEmpty()) {
            return new Path(positions);
        }
        Position firstHuddlePosition = firstHuddle.get();
        int firstHuddlePositionIndex = positions.indexOf(firstHuddlePosition);
        return new Path(positions.subList(0, firstHuddlePositionIndex + 1));
    }

    public boolean hasHuddle(OccupiedPositions occupied) {
        return positions.stream().anyMatch(occupied::existPosition);
    }

    public Optional<Position> findFirstHuddle(OccupiedPositions occupied) {
        return positions.stream().filter(occupied::existPosition).findFirst();
    }
}
