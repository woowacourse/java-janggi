package janggi.domain;

import janggi.domain.position.Position;

import java.util.List;
import java.util.Objects;

public class WayPoints {
    private final List<Position> positions;

    public WayPoints(List<Position> positions) {
        this.positions = positions;
    }

    public boolean isBlocked(Position position) {
        return positions.stream()
                .anyMatch(p -> p.equals(position));
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof WayPoints wayPoints)) return false;

        return Objects.equals(positions, wayPoints.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(positions);
    }
}
