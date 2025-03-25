package model;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Horse extends Piece {

    public Horse(Color color) {
        super(new PieceIdentity(color, PieceType.HORSE));
    }

    @Override
    public Set<Position> calculateMovablePositions(Position startPosition, OccupiedPositions occupiedPositions) {
        return horseDirections().stream()
                .filter(startPosition::canMove)
                .map(directions -> new Path(startPosition, directions))
                .filter(path -> occupiedPositions.isCornerEmpty(path.getCornerPositions()))
                .map(Path::getDestinationPosition)
                .filter(destination -> !destinationIsSameColor(destination, occupiedPositions))
                .collect(Collectors.toSet());
    }

    private static List<List<Direction>> horseDirections() {
        return Direction.getStraightDirection().stream()
                .flatMap(straightDirection -> straightDirection.nextCrossDirection().stream()
                        .map(crossDirection -> List.of(straightDirection, crossDirection))
                ).toList();
    }

    private boolean destinationIsSameColor(Position destination, OccupiedPositions occupiedPositions) {
        return occupiedPositions.existSameColor(destination, identity().getColor());
    }
}
