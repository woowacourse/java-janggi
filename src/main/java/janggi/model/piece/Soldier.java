package janggi.model.piece;

import janggi.model.Color;
import janggi.model.Direction;
import janggi.model.OccupiedPositions;
import janggi.model.PieceIdentity;
import janggi.model.PieceType;
import janggi.model.Position;
import java.util.Set;
import java.util.stream.Collectors;

public class Soldier extends Piece {

    public Soldier(Color color) {
        super(new PieceIdentity(color, PieceType.SOLDIER));
    }

    @Override
    public Set<Position> calculateMovablePositions(Position startPosition, OccupiedPositions occupiedPositions) {
        return Direction.getStraightDirection().stream()
                .filter(this::isNotBack)
                .filter(startPosition::canMove)
                .map(startPosition::move)
                .filter(destination -> destinationIsNotSameColor(occupiedPositions, destination))
                .collect(Collectors.toSet());
    }

    private boolean isNotBack(Direction direction) {
        return direction != Direction.calculateBackDirection(identity().getColor());
    }

    private boolean destinationIsNotSameColor(OccupiedPositions occupiedPositions, Position destination) {
        return !occupiedPositions.existSameColor(destination, identity().getColor());
    }
}
