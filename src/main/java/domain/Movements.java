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
}
