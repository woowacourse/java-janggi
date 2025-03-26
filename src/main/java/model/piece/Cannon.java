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

public class Cannon extends Piece {

    public Cannon(Color color) {
        super(new PieceIdentity(color, PieceType.CANNON));
    }

    @Override
    public Set<Position> calculateMovablePositions(Position startPosition, OccupiedPositions occupiedPositions) {
        return Direction.getStraightDirection().stream().flatMap(straightDirection -> calculateMovableOneSide(
                startPosition,
                occupiedPositions,
                straightDirection
        ).stream()).collect(Collectors.toSet());
    }

    private Set<Position> calculateMovableOneSide(
            Position startPosition,
            OccupiedPositions occupiedPositions,
            Direction straightDirection
    ) {
        Set<Position> movablePositions = new HashSet<>();
        Position currentPosition = startPosition;
        boolean visitedHuddle = false;
        while (currentPosition.canMove(straightDirection)) {
            Position nextPosition = currentPosition.move(straightDirection);
            if (!visitedHuddle && occupiedPositions.existPosition(nextPosition)) {
                if (occupiedPositions.getPieceIdentity(nextPosition).getPieceType() == PieceType.CANNON) {
                    break;
                }
                visitedHuddle = true;
                currentPosition = nextPosition;
                continue;
            }
            if (visitedHuddle && !occupiedPositions.existSameColor(nextPosition, identity().getColor())) {
                movablePositions.add(nextPosition);
            }
            if (visitedHuddle && occupiedPositions.existPosition(nextPosition)) {
                break;
            }
            currentPosition = nextPosition;
        }
        return movablePositions;
    }
}
