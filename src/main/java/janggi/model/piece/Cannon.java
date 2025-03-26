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

public class Cannon extends Piece {

    public Cannon(Color color) {
        super(new PieceIdentity(color, PieceType.CANNON));
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
        boolean visitedHuddle = false;
        while (currentPosition.canMove(direction)) {
            Position nextPosition = currentPosition.move(direction);
            if (!visitedHuddle && occupied.existPosition(nextPosition)) {
                if (occupied.getPieceIdentity(nextPosition).getPieceType() == PieceType.CANNON) {
                    break;
                }
                visitedHuddle = true;
                currentPosition = nextPosition;
                continue;
            }
            if (visitedHuddle && !occupied.existSameColor(nextPosition, identity().getColor())) {
                movablePositions.add(nextPosition);
            }
            if (visitedHuddle && occupied.existPosition(nextPosition)) {
                break;
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
}
