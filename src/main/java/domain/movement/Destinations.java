package domain.movement;

import domain.common.Position;
import java.util.List;

public class Destinations {
    private final List<Position> positions;

    public Destinations(List<Position> positions) {
        List<Position> uniquePositions = positions.stream()
                .distinct()
                .toList();
        validate(uniquePositions);
        this.positions = List.copyOf(uniquePositions);
    }

    private void validate(List<Position> positions) {
        if (positions.isEmpty()) {
            throw new IllegalArgumentException("이동 가능한 목적지가 없습니다.");
        }
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void validateDestinations(Position target) {
        if (!positions.contains(target)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
    }
}
