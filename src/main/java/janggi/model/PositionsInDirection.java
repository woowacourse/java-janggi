package janggi.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PositionsInDirection {
    private final List<Position> positions;

    public PositionsInDirection(List<Position> positions) {
        this.positions = positions;
    }

    public PositionsInDirection getPositionsUntilHuddle(OccupiedPositions occupied) {
        if (!hasHuddle(occupied)) {
            return new PositionsInDirection(positions);
        }
        Position firstHuddlePosition = findFirstHuddle(occupied);
        int firstHuddlePositionIndex = positions.indexOf(firstHuddlePosition);
        return new PositionsInDirection(positions.subList(0, firstHuddlePositionIndex + 1));
    }

    public boolean hasHuddle(OccupiedPositions occupied) {
        return positions.stream().anyMatch(occupied::existPosition);
    }

    public boolean hasHuddleInCorner(OccupiedPositions occupied) {
        if (positions.size() <= 1) {
            return false;
        }
        PositionsInDirection corner = new PositionsInDirection(positions.subList(0, positions.size() - 1));
        return corner.hasHuddle(occupied);
    }

    public Position findFirstHuddle(OccupiedPositions occupied) {
        return positions.stream()
                .filter(occupied::existPosition)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("장애물이 존재하지 않습니다."));
    }

    public boolean isEmpty() {
        return positions.isEmpty();
    }

    public Position lastPosition() {
        if (positions.isEmpty()) {
            throw new IllegalArgumentException("위치가 존재하지 않습니다.");
        }
        return positions.getLast();
    }

    public Set<Position> getCornerPositions() {
        if (positions.size() <= 1) {
            return Collections.emptySet();
        }
        return new HashSet<>(positions.subList(0, positions.size() - 1));
    }

    public Set<Position> getAllPositions() {
        return new HashSet<>(positions);
    }
}
