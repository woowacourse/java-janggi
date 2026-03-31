package position;

import java.util.ArrayList;
import java.util.List;

public class DirectionSequenceResult {
    private final List<Position> positions;

    public DirectionSequenceResult(List<Position> positions) {
        this.positions = List.copyOf(positions);
    }

    public Position lastPosition() {
        if (positions.isEmpty()) {
            throw new IllegalArgumentException("이동 경로가 비어 있어 마지막 위치를 반환할 수 없습니다.");
        }
        return positions.getLast();
    }

    public List<Position> pathPositions() {
        return new ArrayList<>(positions.subList(0, positions.size() - 1));
    }
}
