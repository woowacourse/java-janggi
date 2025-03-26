package model.piece;

import java.util.Set;
import java.util.stream.Collectors;
import model.Color;
import model.Direction;
import model.OccupiedPositions;
import model.PieceIdentity;
import model.PieceType;
import model.Position;

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
