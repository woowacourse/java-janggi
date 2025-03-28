package janggi.domain.piece;

import janggi.domain.piece.movement.Movement;
import java.util.List;
import java.util.function.Consumer;

public class Chariot extends Piece {
    public static final List<Position> INITIAL_POSITIONS_BLUE = List.of(
            new Position(10, 1),
            new Position(10, 9));
    public static final List<Position> INITIAL_POSITIONS_RED = List.of(
            new Position(1, 1),
            new Position(1, 9));

    public Chariot(final Team team) {
        super("차", team);
    }

    @Override
    public Consumer<Pieces> getMovableValidator(final Position beforePosition,
                                                final Position afterPosition) {
        return pieces -> {
            CommonValidator.validateStraightMovement(beforePosition, afterPosition);
            validateNoSameTeamPieceAt(afterPosition, team, pieces);
            validateNoObstaclesOnPath(pieces, beforePosition, afterPosition);
        };
    }

    private void validateNoObstaclesOnPath(
            final Pieces pieces,
            final Position beforePosition,
            final Position afterPosition
    ) {
        Movement movement = afterPosition.subtract(beforePosition);
        Movement nextMovement = Movement.findStraightUnitMovement(movement.x(), movement.y());

        Position currentPosition = beforePosition.plus(nextMovement.x(), nextMovement.y());
        while (!currentPosition.equals(afterPosition)) {
            if (!pieces.get(currentPosition).isNone()) {
                throw new IllegalArgumentException("불가능한 이동입니다");
            }
            currentPosition = currentPosition.plus(nextMovement.x(), nextMovement.y());
        }
    }
}