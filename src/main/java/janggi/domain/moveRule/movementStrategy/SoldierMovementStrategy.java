package janggi.domain.moveRule.movementStrategy;

import janggi.domain.board.Direction;
import janggi.domain.board.Movement;
import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.piece.TeamColor;
import java.util.List;

public class SoldierMovementStrategy implements MovementStrategy {
    private final static List<Movement> SOLDIER_POSSIBLE_MOVEMENT =
            List.of(
                    Movement.from(Direction.UP),
                    Movement.from(Direction.DOWN),
                    Movement.from(Direction.LEFT),
                    Movement.from(Direction.RIGHT)
            );
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
        Direction direction = path.calculateDirection();
        Movement movement = Movement.from(direction);

        if (isBackMovement(direction, teamColor)) {
            return false;
        }
        if (path.isInPalacePath() && IN_PALACE_POSSIBLE_MOVEMENT.contains(movement)) {
            return true;
        }
        return SOLDIER_POSSIBLE_MOVEMENT.contains(movement);
    }

    private boolean isBackMovement(Direction direction, TeamColor color) {
        if (color == TeamColor.BLUE && direction.getX() > 0) {
            return true;
        }
        if (color == TeamColor.RED && direction.getX() < 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<Position> findAllIntermediatePositions(PiecePath path) {
        return List.of();
    }
}
