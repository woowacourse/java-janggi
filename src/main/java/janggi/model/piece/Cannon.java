package janggi.model.piece;

import janggi.model.Color;
import janggi.model.Direction;
import janggi.model.OccupiedPositions;
import janggi.model.PieceIdentity;
import janggi.model.PieceType;
import janggi.model.Position;
import janggi.model.PositionsInDirection;
import java.util.Collections;
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
        return identity().score();
    }

    private Set<Position> calculateMovableOneSide(Direction direction, Position start, OccupiedPositions occupied) {
        PositionsInDirection positions = start.getPositionsInDirection(direction);
        if (!positions.hasHuddle(occupied)) {
            return Collections.emptySet();
        }
        Position huddlePosition = positions.findFirstHuddle(occupied);
        if (occupied.isSameType(huddlePosition, PieceType.CANNON)) {
            return Collections.emptySet();
        }
        return movableAfterHuddle(direction, occupied, huddlePosition);
    }

    private Set<Position> movableAfterHuddle(Direction direction, OccupiedPositions occupied, Position huddlePosition) {
        PositionsInDirection afterHuddlePositionsInDirection = huddlePosition.getPositionsInDirection(direction);
        PositionsInDirection untilHuddle = afterHuddlePositionsInDirection.getPositionsUntilHuddle(occupied);
        if (untilHuddle.isEmpty()) {
            return Collections.emptySet();
        }
        Position lastMovablePosition = untilHuddle.lastPosition();
        if (!occupied.existPosition(lastMovablePosition)) {
            return untilHuddle.getAllPositions();
        }
        if (occupied.existSameColor(lastMovablePosition, getColor())) {
            return untilHuddle.getCornerPositions();
        }
        if (occupied.isSameType(lastMovablePosition, PieceType.CANNON)) {
            return untilHuddle.getCornerPositions();
        }
        return untilHuddle.getAllPositions();
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
