package janggi.domain.piece.impl;

import janggi.domain.piece.CommonValidator;
import janggi.domain.piece.Team;
import janggi.domain.movement.Movement;
import janggi.domain.piece.PalacePiece;
import janggi.domain.piece.Pieces;
import janggi.domain.piece.Position;
import java.util.List;
import java.util.function.Consumer;

public class Chariot extends PalacePiece {
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
            validateNoObstaclesOnStraightPath(pieces, beforePosition, afterPosition);
        };
    }

    @Override
    public Consumer<Pieces> getPalaceMovableValidator(final Position beforePosition, final Position afterPosition) {
        return pieces -> {
            CommonValidator.validateLinearMovement(beforePosition, afterPosition);
            validateNoSameTeamPieceAt(afterPosition, team, pieces);
            validateNoObstaclesOnPath(pieces, beforePosition, afterPosition);
        };
    }

    private void validateNoObstaclesOnStraightPath(
            final Pieces pieces,
            final Position beforePosition,
            final Position afterPosition
    ) {
        Movement movement = afterPosition.getMovementTo(beforePosition);
        Movement nextMovement = Movement.findStraightUnitMovement(movement.x(), movement.y());

        Position currentPosition = beforePosition.plus(nextMovement.x(), nextMovement.y());
        while (!currentPosition.equals(afterPosition)) {
            if (!pieces.get(currentPosition).isNone()) {
                throw new IllegalArgumentException("불가능한 이동입니다");
            }
            currentPosition = currentPosition.plus(nextMovement.x(), nextMovement.y());
        }
    }

    private void validateNoObstaclesOnPath(
            final Pieces pieces,
            final Position beforePosition,
            final Position afterPosition
    ) {
        Movement movement = afterPosition.getMovementTo(beforePosition);
        Movement nextMovement = Movement.findUnitMovement(movement.x(), movement.y());

        Position currentPosition = beforePosition.plus(nextMovement.x(), nextMovement.y());
        while (!currentPosition.equals(afterPosition)) {
            if (!pieces.get(currentPosition).isNone()) {
                throw new IllegalArgumentException("불가능한 이동입니다");
            }
            currentPosition = currentPosition.plus(nextMovement.x(), nextMovement.y());
        }
    }

    @Override
    public int getScore() {
        return 13;
    }
}