package model;

import java.util.HashSet;
import java.util.Set;

public class King extends Piece {

    protected King(Color color) {
        super(new PieceIdentity(color, PieceType.KING));
    }

    @Override
    public Set<Position> calculateMovablePositions(Position startPosition, OccupiedPositions occupiedPositions) {
        Set<Position> movablePositions = new HashSet<>();
        for (Direction direction : Direction.values()) {
            if (!startPosition.canMove(direction)) {
                continue;
            }
            Position nextPosition = startPosition.move(direction);
            if (!occupiedPositions.existSameColor(nextPosition, identity().getColor())) {
                movablePositions.add(nextPosition);
            }
        }
        return movablePositions;
    }
}
