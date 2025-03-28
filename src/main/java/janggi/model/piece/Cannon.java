package janggi.model.piece;

import janggi.model.Color;
import janggi.model.Direction;
import janggi.model.OccupiedPositions;
import janggi.model.Path;
import janggi.model.PieceIdentity;
import janggi.model.PieceType;
import janggi.model.Position;
import janggi.model.PositionsInDirection;
import java.util.HashSet;
import java.util.Optional;
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

    @Override
    public double getScore() {
        return 7;
    }

    private Set<Position> calculateMovableOneSide(Direction direction, Position start, OccupiedPositions occupied) {
        PositionsInDirection positionsInDirection = start.getPositionsInDirection(direction);
        if (!positionsInDirection.hasHuddle(occupied)) {
            return new HashSet<>();
        }
        Optional<Position> firstHuddle = positionsInDirection.findFirstHuddle(occupied);
        if (firstHuddle.isEmpty()) {
            return new HashSet<>();
        }
        if (occupied.getPieceIdentity(firstHuddle.get()).getPieceType() == PieceType.CANNON) {
            return new HashSet<>();
        }
        PositionsInDirection movablePositionsInDirection = firstHuddle.get().getPositionsInDirection(direction);
        Path pathUntilHuddle = movablePositionsInDirection.getPathUntilHuddle(occupied);
        Position destination = pathUntilHuddle.getDestinationPosition();
        if (occupied.existSameColor(destination, identity().getColor())) {
            return pathUntilHuddle.getCornerPositionSet();
        }
        if (!occupied.existPosition(destination)) {
            return pathUntilHuddle.getAllPositionSet();
        }
        if (occupied.getPieceIdentity(destination).getPieceType() == PieceType.CANNON) {
            return pathUntilHuddle.getCornerPositionSet();
        }
        return pathUntilHuddle.getAllPositionSet();
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
