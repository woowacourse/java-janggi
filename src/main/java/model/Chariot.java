package model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Chariot extends Piece {

    public Chariot(Color color) {
        super(new PieceIdentity(color, PieceType.CHARIOT));
    }

    @Override
    public Set<Position> calculateMovablePositions(
            Position startPosition,
            OccupiedPositions occupiedPositions
    ) {
        List<Direction> straightDirections = Direction.getStraightDirection();
        Position currentPosition = startPosition;
        while(!currentPosition.canMove())
        Set<Position> path = new HashSet<>();
        for (int column = startPosition.column() + 1; column < 10; ++column) {
            Position nextPosition = new Position(startPosition.row(), column);
            if (!occupiedPositions.existPosition(nextPosition)) {
                path.add(nextPosition);
            } else {
                if (!occupiedPositions.existSameColor(nextPosition, identity().getColor())) {
                    path.add(nextPosition);
                }
                break;
            }
        }
        for (int column = startPosition.column() - 1; column > 0; --column) {
            Position nextPosition = new Position(startPosition.row(), column);
            if (!occupiedPositions.existPosition(nextPosition)) {
                path.add(nextPosition);
            } else {
                if (!occupiedPositions.existSameColor(nextPosition, identity().getColor())) {
                    path.add(nextPosition);
                }
                break;
            }
        }
        for (int row = startPosition.row() - 1; row > 0; --row) {
            Position nextPosition = new Position(row, startPosition.column());
            if (!occupiedPositions.existPosition(nextPosition)) {
                path.add(nextPosition);
            } else {
                if (!occupiedPositions.existSameColor(nextPosition, identity().getColor())) {
                    path.add(nextPosition);
                }
                break;
            }
        }
        for (int row = startPosition.row() + 1; row < 11; ++row) {
            Position nextPosition = new Position(row, startPosition.column());
            if (!occupiedPositions.existPosition(nextPosition)) {
                path.add(nextPosition);
            } else {
                if (!occupiedPositions.existSameColor(nextPosition, identity().getColor())) {
                    path.add(nextPosition);
                }
                break;
            }
        }
        return path;
    }
}
