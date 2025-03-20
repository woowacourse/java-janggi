package domain;

import java.util.List;

public class Movements {
    private final List<Movement> movements;

    public Movements(List<Movement> movements) {
        this.movements = movements;
    }

    public boolean canMoveFromTo(Position startPosition, Position endPosition) {
        return movements.stream()
                .anyMatch(movement -> movement.isValidMove(startPosition, endPosition));
    }

    public List<Position> findIntermediatePositions(Position startPosition, Position endPosition) {
        return movements.stream()
                .filter(movement -> movement.isValidMove(startPosition, endPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지정한 포지션으로 이동할 수 없습니다."))
                .findIntermediatePositions(startPosition, endPosition);
    }
}
