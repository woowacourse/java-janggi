package model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Elephant extends Piece {

    public Elephant(Color color) {
        super(new PieceIdentity(color, PieceType.ELEPHANT));
    }

    @Override
    public Set<Position> calculateMovablePositions(Position startPosition, OccupiedPositions occupiedPositions) {
        Set<Position> movablePositions = new HashSet<>();
        for (Direction direction : Direction.getStraightDirection()) {
            for (Direction crossDirection : direction.nextCrossDirection()) {
                List<Direction> directions = List.of(direction, crossDirection, crossDirection);
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
