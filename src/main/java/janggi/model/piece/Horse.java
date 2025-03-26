package janggi.model.piece;

import janggi.model.Color;
import janggi.model.Direction;
import janggi.model.OccupiedPositions;
import janggi.model.PathDirections;
import janggi.model.PieceIdentity;
import janggi.model.PieceType;
import janggi.model.Position;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import janggi.model.Path;

public class Horse extends Piece {

    public Horse(Color color) {
        super(new PieceIdentity(color, PieceType.HORSE));
    }

    @Override
    public Set<Position> calculateMovablePositions(Position start, OccupiedPositions occupied) {
        return horseDirections().stream()
                .filter(start::canMove)
                .map(pathDirections -> pathDirections.convertPath(start))
                .filter(path -> occupied.isCornerEmpty(path.getCornerPositions()))
                .map(Path::getDestinationPosition)
                .filter(destination -> destinationIsNotSameColor(destination, occupied))
                .collect(Collectors.toSet());
    }

    private static List<PathDirections> horseDirections() {
        return Direction.getStraightDirection().stream()
                .flatMap(straightDirection -> straightDirection.nextCrossDirection().stream()
                        .map(crossDirection -> new PathDirections(List.of(straightDirection, crossDirection)))
                ).toList();
    }

    private boolean destinationIsNotSameColor(Position destination, OccupiedPositions occupied) {
        return !occupied.existSameColor(destination, identity().getColor());
    }
}
