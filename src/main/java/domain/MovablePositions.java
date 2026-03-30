package domain;

import java.util.List;

public class MovablePositions {
    private final List<Position> positions;

    public MovablePositions(List<Position> positions) {
        this.positions = List.copyOf(positions);
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void validateDestinations(Position target) {
        if (!positions.contains(target)) {
            throw new IllegalArgumentException("선택할 수 없는 기물입니다.");
        }
    }
}
