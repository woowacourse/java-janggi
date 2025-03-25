package model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Horse extends Piece {

    public Horse(Color color) {
        super(new PieceIdentity(color, PieceType.HORSE));
    }

    @Override
    public Set<Position> calculateMovablePositions(Position startPosition, OccupiedPositions occupiedPositions) {
        Set<Position> movablePositions = new HashSet<>();
        for (Direction direction : Direction.getStraightDirection()) {
            for (Direction crossDirection : direction.nextCrossDirection()) {
                List<Direction> directions = List.of(direction, crossDirection);
                if (!startPosition.canMove(directions)) {
                    continue;
                }
                Path path = new Path(startPosition, directions);
                if (!occupiedPositions.arePositionsEmpty(path.getCornerPositions())) {
                    continue;
                }
                if (!occupiedPositions.existSameColor(path.getDestinationPosition(), identity().getColor())) {
                    movablePositions.add(path.getDestinationPosition());
                }

            }
        }
        return movablePositions;
    }
}
