package model.piece;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import model.Color;
import model.Direction;
import model.OccupiedPositions;
import model.PieceIdentity;
import model.PieceType;
import model.Position;

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
