package domain.movement;

import domain.common.Position;
import java.util.List;

public class Path {
    private final List<Position> positions;

    public Path(List<Position> positions) {
        if (positions == null || positions.isEmpty()) {
            throw new IllegalArgumentException("불가능한 경로입니다.");
        }
        this.positions = List.copyOf(positions);
    }

    public Position getDestination() {
        return positions.getLast();
    }

    public List<Position> getObstacles() {
        return positions.subList(0, positions.size() - 1);
    }

    public List<Position> getPositions() {
        return positions;
    }
}
