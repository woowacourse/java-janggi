package janggi.domain.space;

import java.util.List;

public class Destinations {
    private final List<Position> positions;

    public Destinations(List<Position> positions) {
        validate(positions);
        this.positions = List.copyOf(positions);
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
