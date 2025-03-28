package janggi.model.piece;

import janggi.model.Color;
import janggi.model.Direction;
import janggi.model.OccupiedPositions;
import janggi.model.Path;
import janggi.model.PieceIdentity;
import janggi.model.PieceType;
import janggi.model.Position;
import janggi.model.PositionsInDirection;
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

    @Override
    public double getScore() {
        return 13;
    }

    private Set<Position> calculateMovableOneSide(Direction direction, Position start, OccupiedPositions occupied) {
        PositionsInDirection positionsInDirection = start.getPositionsInDirection(direction);
        Path pathUntilHuddle = positionsInDirection.getPathUntilHuddle(occupied);
        Position destination = pathUntilHuddle.getDestinationPosition();
        if (!occupied.existSameColor(destination, identity().getColor())) {
            return pathUntilHuddle.getAllPositionSet();
        }
        return pathUntilHuddle.getCornerPositionSet();
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
