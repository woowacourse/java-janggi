package janggi.model.piece;

import janggi.model.Color;
import janggi.model.Direction;
import janggi.model.OccupiedPositions;
import janggi.model.PieceIdentity;
import janggi.model.PieceType;
import janggi.model.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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

    private Set<Position> calculateMovableOneSide(Direction direction, Position start, OccupiedPositions occupied) {
        List<Position> positionsInDirection = getPositionsInDirection(direction, start);
        Optional<Position> huddle = findHuddle(positionsInDirection, occupied);
        return huddle.filter(position -> !isCannon(position, occupied)).map(position -> {
            List<Position> positionsAfterHuddle = getPositionsAfterHuddle(positionsInDirection, position);
            return findMovablePositionsAfterHuddle(positionsAfterHuddle, occupied);
        }).orElse(Collections.emptySet());
    }


    private List<Position> getPositionsInDirection(Direction direction, Position start) {
        List<Position> positions = new ArrayList<>();
        Position currentPosition = start;
        while (currentPosition.canMove(direction)) {
            currentPosition = currentPosition.move(direction);
            positions.add(currentPosition);
        }
        return positions;
    }

    private Optional<Position> findHuddle(List<Position> positions, OccupiedPositions occupied) {
        return positions.stream().filter(occupied::existPosition).findFirst();
    }

    private boolean isCannon(Position position, OccupiedPositions occupied) {
        return occupied.getPieceIdentity(position).getPieceType() == PieceType.CANNON;
    }

    private List<Position> getPositionsAfterHuddle(List<Position> positions, Position huddle) {
        return positions.subList(positions.indexOf(huddle) + 1, positions.size());
    }

    private Set<Position> findMovablePositionsAfterHuddle(
            List<Position> positionsAfterHuddle,
            OccupiedPositions occupied
    ) {
        Optional<Position> huddle = findHuddle(positionsAfterHuddle, occupied);
        return huddle.map(position -> {
            int huddleIndex = positionsAfterHuddle.indexOf(position);
            Set<Position> movablePositions = new HashSet<>(positionsAfterHuddle.subList(0, huddleIndex));
            if (!occupied.existSameColor(position, identity().getColor()) && !isCannon(position, occupied)) {
                movablePositions.add(position);
            }
            return movablePositions;
        }).orElseGet(() -> new HashSet<>(positionsAfterHuddle));
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
