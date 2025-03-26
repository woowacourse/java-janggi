package model.piece;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import model.Color;
import model.Direction;
import model.OccupiedPositions;
import model.PieceIdentity;
import model.PieceType;
import model.Position;

public class Chariot extends Piece {

    public Chariot(Color color) {
        super(new PieceIdentity(color, PieceType.CHARIOT));
    }

    @Override
    public Set<Position> calculateMovablePositions(Position startPosition, OccupiedPositions occupiedPositions) {
        return Direction.getStraightDirection().stream().flatMap(straightDirection -> calculateMovableOneSide(
                straightDirection,
                startPosition,
                occupiedPositions
        ).stream()).collect(Collectors.toSet());
    }

    public Set<Position> calculateMovableOneSide(
            Direction direction,
            Position startPosition,
            OccupiedPositions occupiedPositions
    ) {
        Set<Position> movablePositions = new HashSet<>();
        Position currentPosition = startPosition;
        while (currentPosition.canMove(direction) && isNotCurrentExist(startPosition, occupiedPositions, currentPosition)) {
            Position nextPosition = currentPosition.move(direction);
            if (!occupiedPositions.existSameColor(nextPosition, identity().getColor())) {
                movablePositions.add(nextPosition);
            }
            currentPosition = nextPosition;
        }
        return movablePositions;
    }

    private static boolean isNotCurrentExist(
            Position startPosition,
            OccupiedPositions occupiedPositions,
            Position currentPosition
    ) {
        return !(currentPosition != startPosition && occupiedPositions.existPosition(currentPosition));
    }
}
