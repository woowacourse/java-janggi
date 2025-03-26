package janggi.model.piece;

import janggi.model.Color;
import janggi.model.Direction;
import janggi.model.OccupiedPositions;
import janggi.model.PieceIdentity;
import janggi.model.PieceType;
import janggi.model.Position;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class King extends Piece {

    public King(Color color) {
        super(new PieceIdentity(color, PieceType.KING));
    }

    @Override
    public Set<Position> calculateMovablePositions(Position startPosition, OccupiedPositions occupiedPositions) {
        return Arrays.stream(Direction.values())
                .filter(startPosition::canMove)
                .map(startPosition::move)
                .filter(destination -> destinationIsNotSameColor(occupiedPositions, destination))
                .collect(Collectors.toSet());
    }

    private boolean destinationIsNotSameColor(OccupiedPositions occupiedPositions, Position destination) {
        return !occupiedPositions.existSameColor(destination, identity().getColor());
    }
}
