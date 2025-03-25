package model;

import java.util.HashSet;
import java.util.Set;

public class Soldier extends Piece {

    protected Soldier(Color color) {
        super(new PieceIdentity(color, PieceType.SOLDIER));
    }

    @Override
    public Set<Position> calculateMovablePositions(Position startPosition, OccupiedPositions occupiedPositions) {
        Set<Position> movablePositions = new HashSet<>();
        Direction backDirection = Direction.calculateBackDirection(identity().getColor());
        for (Direction direction : Direction.getStraightDirection()) {
            if (direction == backDirection) {
                continue;
            }
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
