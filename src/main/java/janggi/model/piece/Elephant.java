package janggi.model.piece;

import janggi.model.Color;
import janggi.model.Direction;
import janggi.model.OccupiedPositions;
import janggi.model.Path;
import janggi.model.PathDirections;
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
    public Set<Position> calculateMovablePositions(Position start, OccupiedPositions occupied) {
        return elephantDirections().stream()
                .filter(start::canMove)
                .map(pathDirections -> pathDirections.convertPath(start))
                .filter(path -> occupied.isCornerEmpty(path.getCornerPositions()))
                .map(Path::getDestinationPosition)
                .filter(destination -> destinationIsNotSameColor(destination, occupied))
                .collect(Collectors.toSet());
    }

    private static List<PathDirections> elephantDirections() {
        return Direction.getStraightDirection().stream()
                .flatMap(straightDirection -> straightDirection.nextCrossDirection().stream()
                        .map(crossDirection -> new PathDirections(List.of(
                                straightDirection,
                                crossDirection,
                                crossDirection
                        )))
                ).toList();
    }

    private boolean destinationIsNotSameColor(Position destination, OccupiedPositions occupied) {
        return !occupied.existSameColor(destination, identity().getColor());
    }
}
