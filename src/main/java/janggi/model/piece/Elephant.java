package janggi.model.piece;

import janggi.model.Color;
import janggi.model.Direction;
import janggi.model.OccupiedPositions;
import janggi.model.Directions;
import janggi.model.PieceIdentity;
import janggi.model.PieceType;
import janggi.model.Position;
import janggi.model.PositionsInDirection;
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
                .map(directions -> directions.convertPositionsInDirections(start))
                .filter(positionsInDirection -> !positionsInDirection.hasHuddleInCorner(occupied))
                .map(PositionsInDirection::lastPosition)
                .filter(destination -> destinationIsNotSameColor(destination, occupied))
                .collect(Collectors.toSet());
    }

    @Override
    public double getScore() {
        return identity().score();
    }

    private static List<Directions> elephantDirections() {
        return Direction.getStraightDirection().stream()
                .flatMap(straightDirection -> straightDirection.nextCrossDirection().stream()
                        .map(crossDirection -> new Directions(List.of(
                                straightDirection,
                                crossDirection,
                                crossDirection
                        )))
                ).toList();
    }

    private boolean destinationIsNotSameColor(Position destination, OccupiedPositions occupied) {
        return !occupied.existSameColor(destination, identity().color());
    }
}
