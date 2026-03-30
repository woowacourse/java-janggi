package janggi.domain;

import janggi.domain.position.Position;

import java.util.List;

public class WayPoints {
    private final List<Position> positions;

    public WayPoints(List<Position> positions) {
        this.positions = positions;
    }

    public boolean isBlocked(Position position) {
        return positions.stream()
                .anyMatch(p -> p.equals(position));
    }
}
