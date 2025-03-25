package model;

import java.util.HashSet;
import java.util.Set;

public class Chariot extends Piece {

    public Chariot(Color color) {
        super(new PieceIdentity(color, PieceType.CHARIOT));
    }

    @Override
    public Set<Position> calculateMovablePositions(Position startPosition, OccupiedPositions occupiedPositions) {
        Set<Position> movablePositions = new HashSet<>();
        for (Direction direction : Direction.getStraightDirection()) {
            Position currentPosition = startPosition;
            while(currentPosition.canMove(direction)) {
                Position nextPosition = currentPosition.move(direction);
                if (!occupiedPositions.existSameColor(nextPosition, identity().getColor())) {
                    movablePositions.add(nextPosition);
                }
                if (occupiedPositions.existPosition(nextPosition)) {
                    break;
                }
                currentPosition = nextPosition;
            }
        }
        return movablePositions;
    }
}
