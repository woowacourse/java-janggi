package janggi.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Path {
    private final List<Position> positions;

    public Path(List<Position> positions) {
        if (positions.isEmpty()) {
            throw new IllegalArgumentException("경로에 적어도 한 개 이상의 위치가 있어야 합니다.");
        }
        this.positions = new ArrayList<>(positions);
    }

    public Path(Position destinationPosition, List<Position> positions) {
        this.positions = new ArrayList<>(positions);
        this.positions.add(destinationPosition);
    }

    public Position getDestinationPosition() {
        return positions.getLast();
    }

    public List<Position> getCornerPositions() {
        if (this.positions.isEmpty()) {
            return List.of();
        }
        return positions.subList(0, this.positions.size() - 1);
    }

    public Set<Position> getAllPositionSet () {
        return new HashSet<>(positions);
    }

    public Position getFirstPosition() {
        return positions.getFirst();
    }

    public Set<Position> getCornerPositionSet() {
        return new HashSet<>(getCornerPositions());
    }
}
