package domain.strategy;

import domain.Position;
import java.util.List;

public class Path {
    private final List<Position> positions;

    public Path(List<Position> positions) {
        validate(positions);
        this.positions = List.copyOf(positions);
    }

    private void validate(List<Position> positions) {
        if (positions == null || positions.isEmpty()) {
            throw new IllegalStateException("경로가 존재하지 않습니다.");
        }
    }

    public Position getDestination() {
        return positions.getLast();
    }

    public List<Position> getObstacles() {
        if (positions.size() <= 1) {
            return List.of();
        }
        return positions.subList(0, positions.size() - 1);
    }

    public List<Position> getPositions() {
        return positions;
    }
}
