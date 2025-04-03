package janggi.direction;

import janggi.position.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Movements {

    private final List<Movement> movements;

    public Movements(final List<Movement> movements) {
        this.movements = new ArrayList<>(movements);
    }

    public Movement findMovements(final Position startPosition, final Position arrivalPosition,
                                  final PieceType pieceType) {
        int dy = arrivalPosition.calculateDifferenceForY(startPosition);
        int dx = arrivalPosition.calculateDifferenceForX(startPosition);
        if (pieceType == PieceType.CHARIOT && (dy == 0 || dx == 0)) {
            dy = calculateUnit(dy);
            dx = calculateUnit(dx);
        }
        final int y = dy;
        final int x = dx;
        return movements.stream()
                .filter(movement -> movement.isSameMovement(y, x))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 적절한 움직임이 아닙니다."));
    }

    public void add(final Movements givenMovements) {
        movements.addAll(givenMovements.getMovements());
    }

    private int calculateUnit(final int difference) {
        if (difference == 0) {
            return difference;
        }
        return difference / Math.abs(difference);
    }

    public List<Movement> getMovements() {
        return Collections.unmodifiableList(movements);
    }
}
