package janggi.domain.moveRule.movementStrategy;

import janggi.domain.board.Direction;
import janggi.domain.board.Movement;
import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.piece.TeamColor;
import java.util.List;

public class PalaceMovementStrategy implements MovementStrategy {
    private static final List<Movement> IN_PALACE_POSSIBLE_MOVEMENT =
            List.of(
                    Movement.from(Direction.UP),
                    Movement.from(Direction.DOWN),
                    Movement.from(Direction.LEFT),
                    Movement.from(Direction.RIGHT),
                    Movement.from(Direction.DOWN_LEFT),
                    Movement.from(Direction.DOWN_RIGHT),
                    Movement.from(Direction.UP_LEFT),
                    Movement.from(Direction.UP_RIGHT)
            );

    @Override
    public boolean isValidMovement(PiecePath path, TeamColor teamColor) {
        if (path.isDiagonal()) {
            return path.isPalaceDiagonalLine();
        }
        return IN_PALACE_POSSIBLE_MOVEMENT.stream()
                .anyMatch(path::canReachToDestination);
    }

    @Override
    public List<Position> findAllIntermediatePositions(PiecePath path) {
        return List.of();
    }
}
