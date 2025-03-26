package janggi.model.piece;

import janggi.model.Color;
import janggi.model.Direction;
import janggi.model.OccupiedPositions;
import janggi.model.PieceIdentity;
import janggi.model.PieceType;
import janggi.model.Position;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
        List<Position> positionsInDirection = getPositionsInDirection(direction, start);
        return findFirstPiece(positionsInDirection, occupied).map(huddle -> {
            int huddleIndex = positionsInDirection.indexOf(huddle);
            Set<Position> movablePositions = new HashSet<>(positionsInDirection.subList(0, huddleIndex));
            if (!occupied.existSameColor(huddle, identity().getColor())) {
                movablePositions.add(huddle);
            }
            return movablePositions;
        }).orElseGet(() -> new HashSet<>(positionsInDirection));
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

    private Optional<Position> findFirstPiece(List<Position> positions, OccupiedPositions occupied) {
        return positions.stream().filter(occupied::existPosition).findFirst();
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
