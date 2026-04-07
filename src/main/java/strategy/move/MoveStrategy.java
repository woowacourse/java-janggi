package strategy.move;

import domain.board.Direction;
import domain.board.MovePath;
import domain.piece.Piece;
import domain.board.Position;
import domain.board.Route;
import domain.piece.TeamColor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface MoveStrategy {

    List<MovePath> getPaths(TeamColor teamColor);

    default List<Route> makeRoutes(Position curPos, TeamColor teamColor) {
        final List<Route> validRoutes = new ArrayList<>();
        final List<MovePath> paths = getPaths(teamColor);

        for (final MovePath path : paths) {
            createRoute(curPos, path.steps()).ifPresent(validRoutes::add);
        }

        return validRoutes;
    }

    private Optional<Route> createRoute(Position startPos, List<Direction> steps) {
        final List<Position> intermediates = new ArrayList<>();
        Position currentPos = startPos;

        for (int index = 0; index < steps.size(); index++) {
            final Optional<Position> nextPosition = findNextPosition(currentPos, steps.get(index));
            if (nextPosition.isEmpty()) {
                return Optional.empty();
            }
            currentPos = nextPosition.get();

            if (isIntermediateStep(index, steps.size())) {
                intermediates.add(currentPos);
            }
        }

        return Optional.of(new Route(startPos, currentPos, intermediates));
    }

    private Optional<Position> findNextPosition(Position currentPos, Direction direction) {
        try {
            return Optional.of(currentPos.next(direction));
        } catch (IllegalArgumentException exception) {
            return Optional.empty();
        }
    }

    private boolean isIntermediateStep(int stepIndex, int totalSteps) {
        return stepIndex < totalSteps - 1;
    }

    default boolean canMove(Route route, List<Piece> blockingPieces, Optional<Piece> destinationPiece) {
        return blockingPieces.isEmpty();
    }

}


