package janggi.model.piece;

import janggi.model.Color;
import janggi.model.Direction;
import janggi.model.OccupiedPositions;
import janggi.model.PieceIdentity;
import janggi.model.PieceType;
import janggi.model.Position;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Chariot extends Piece {

    public Chariot(Color color) {
        super(new PieceIdentity(color, PieceType.CHARIOT));
    }

    @Override
    public Set<Position> calculateMovablePositions(Position start, OccupiedPositions occupied) {
        return Direction.allDirections().stream()
                .flatMap(direction -> calculateMovableOneSide(direction, start, occupied).stream())
                .filter(destination -> isCastleRule(start, destination))
                .collect(Collectors.toSet());
    }

    private Set<Position> calculateMovableOneSide(Direction direction, Position start, OccupiedPositions occupied) {
        Set<Position> movablePositions = new HashSet<>();
        Position currentPosition = start;
        while (currentPosition.canMove(direction) && isNotCurrentExist(start, occupied, currentPosition)) {
            Position nextPosition = currentPosition.move(direction);
            if (!occupied.existSameColor(nextPosition, identity().getColor())) {
                movablePositions.add(nextPosition);
            }
            currentPosition = nextPosition;
        }
        return movablePositions;
    }

    private boolean isCastleRule(Position start, Position destination) {
        if (start.isInCastle()) {
            return isMovablePosition(start, destination);
        }
        return !start.isDestinationCross(destination);
    }

    private boolean isMovablePosition(Position start, Position destination) {
        return !(start.isDestinationCross(destination) && !destination.isInCastle());
    }

    private boolean isNotCurrentExist(Position start, OccupiedPositions occupied, Position current) {
        return !(current != start && occupied.existPosition(current));
    }
}
