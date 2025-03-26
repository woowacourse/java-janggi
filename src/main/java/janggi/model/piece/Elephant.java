package janggi.model.piece;

import janggi.model.Color;
import janggi.model.Direction;
import janggi.model.OccupiedPositions;
import janggi.model.Path;
import janggi.model.PieceIdentity;
import janggi.model.PieceType;
import janggi.model.Position;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Elephant extends Piece {

    public Elephant(Color color) {
        super(new PieceIdentity(color, PieceType.ELEPHANT));
    }

    @Override
    public Set<Position> calculateMovablePositions(Position startPosition, OccupiedPositions occupiedPositions) {
        return elephantDirections().stream()
                .filter(startPosition::canMove)
                .map(directions -> new Path(startPosition, directions))
                .filter(path -> occupiedPositions.isCornerEmpty(path.getCornerPositions()))
                .map(Path::getDestinationPosition)
                .filter(destination -> destinationIsNotSameColor(destination, occupiedPositions))
                .collect(Collectors.toSet());
    }

    private static List<List<Direction>> elephantDirections() {
        return Direction.getStraightDirection().stream()
                .flatMap(straightDirection -> straightDirection.nextCrossDirection().stream()
                        .map(crossDirection -> List.of(straightDirection, crossDirection, crossDirection))
                ).toList();
    }

    private boolean destinationIsNotSameColor(Position destination, OccupiedPositions occupiedPositions) {
        return !occupiedPositions.existSameColor(destination, identity().getColor());
    }
}
