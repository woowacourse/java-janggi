package model;

import java.util.HashSet;
import java.util.Set;

public class Cannon extends Piece {

    public Cannon(Color color) {
        super(new PieceIdentity(color, PieceType.CANNON));
    }

    @Override
    public Set<Position> calculateMovablePositions(Position startPosition, OccupiedPositions occupiedPositions) {
        Set<Position> movablePositions = new HashSet<>();
        for (Direction direction : Direction.getStraightDirection()) {
            Position currentPosition = startPosition;
            boolean visitedHuddle = false;
            while (currentPosition.canMove(direction)) {
                Position nextPosition = currentPosition.move(direction);
                if (!visitedHuddle && occupiedPositions.existPosition(nextPosition)) {
                    if (occupiedPositions.getPieceIdentity(nextPosition).getPieceType() == PieceType.CANNON) {
                        break;
                    }
                    visitedHuddle = true;
                    currentPosition = nextPosition;
                    continue;
                }
                if (visitedHuddle && !occupiedPositions.existSameColor(nextPosition, identity().getColor())) {
                    movablePositions.add(nextPosition);
                }
                if (visitedHuddle && occupiedPositions.existPosition(nextPosition)) {
                    break;
                }
                currentPosition = nextPosition;
            }
        }
        return movablePositions;
    }
}
