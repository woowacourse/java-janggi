package domain;

import java.util.List;

public class Path {
    private final List<Position> positions;

    public Path(List<Position> positions) {
        this.positions = List.copyOf(positions);
    }

    public boolean isEmpty() {
        return positions.isEmpty();
    }

    public Position getDestination() {
        if (isEmpty()) {
            throw new IllegalStateException("경로가 존재하지 않습니다.");
        }
        return positions.getLast();
    }

    public List<Position> getObstacles() {
        if (isEmpty()) {
            return List.of();
        }
        return positions.subList(0, positions.size() - 1);
    }

    public List<Position> getPositions() {
        return positions;
    }
}
