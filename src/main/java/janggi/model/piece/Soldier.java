package janggi.model.piece;

import janggi.model.CastleArea;
import janggi.model.Color;
import janggi.model.Direction;
import janggi.model.OccupiedPositions;
import janggi.model.PieceIdentity;
import janggi.model.PieceType;
import janggi.model.Position;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Soldier extends Piece {

    public Soldier(Color color) {
        super(new PieceIdentity(color, PieceType.SOLDIER));
    }

    @Override
    public Set<Position> calculateMovablePositions(Position start, OccupiedPositions occupied) {
        return Stream.concat(
                        Direction.getStraightDirection().stream(),
                        calculateCastleCrossDirection(start).stream()
                )
                .filter(this::isNotBack)
                .filter(start::canMove)
                .map(start::move)
                .filter(destination -> destinationIsNotSameColor(occupied, destination))
                .collect(Collectors.toSet());
    }

    @Override
    public double getScore() {
        return identity().score();
    }

    private List<Direction> calculateCastleCrossDirection(Position start) {
        if (!start.isInCastle()) {
            return List.of();
        }
        return CastleArea.fromByPosition(start).filterCrossDirection();
    }

    private boolean isNotBack(Direction direction) {
        return !Direction.calculateBackDirection(identity().color()).contains(direction);
    }

    private boolean destinationIsNotSameColor(OccupiedPositions occupied, Position destination) {
        return !occupied.existSameColor(destination, identity().color());
    }
}
