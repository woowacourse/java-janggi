package janggi.domain.piece.movement.normal.fixed;

import janggi.domain.piece.pieces.PiecesView;
import janggi.domain.piece.Position;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ElephantMovementStrategy extends FixedMovementStrategy {

    private static final List<Vector> VERTICAL_MOVEMENT_VECTORS = List.of(
        new Vector(0, 1),
        new Vector(1, 2),
        new Vector(2, 3)
    );

    private static final List<Vector> HORIZONTAL_MOVEMENT_VECTORS = List.of(
        new Vector(1, 0),
        new Vector(2, 1),
        new Vector(3, 2)
    );

    @Override
    public boolean isLegalDestination(Position origin, Position destination) {
        return isVerticalMove(origin, destination) || isHorizontalMove(origin, destination);
    }

    @Override
    public PiecesView getAllPiecesOnPath(PiecesView map, Position origin, Position destination) {
        return map.getPiecesOnPath(findPathsToDestination(origin, destination));
    }

    private Set<Position> findPathsToDestination(Position origin, Position destination) {
        if (isVerticalMove(origin, destination)) {
            return findAllMovablePositions(origin, destination, VERTICAL_MOVEMENT_VECTORS);
        }
        return findAllMovablePositions(origin, destination, HORIZONTAL_MOVEMENT_VECTORS);
    }

    private Set<Position> findAllMovablePositions(
        Position origin,
        Position destination,
        List<Vector> movePatterns
    ) {

        return movePatterns.stream()
            .map(pattern -> pattern.apply(origin, destination))
            .collect(Collectors.toSet());
    }

    private boolean isVerticalMove(Position origin, Position destination) {
        return VERTICAL_MOVEMENT_VECTORS.getLast().hasRelativeOffsetFrom(origin, destination);
    }

    private boolean isHorizontalMove(Position origin, Position destination) {
        return HORIZONTAL_MOVEMENT_VECTORS.getLast().hasRelativeOffsetFrom(origin, destination);
    }
}
