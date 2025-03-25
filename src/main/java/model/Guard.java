package model;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Guard extends Piece {

    protected Guard(Color color) {
        super(new PieceIdentity(color, PieceType.GUARD));
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
