package janggi.domain.piece.impl;

import janggi.domain.piece.CommonValidator;
import janggi.domain.piece.Team;
import janggi.domain.movement.Movement;
import janggi.domain.piece.PalacePiece;
import janggi.domain.piece.Pieces;
import janggi.domain.piece.Position;
import java.util.List;
import java.util.function.Consumer;

public class Cannon extends PalacePiece {
    public static final List<Position> INITIAL_POSITIONS_BLUE = List.of(
            new Position(8, 2),
            new Position(8, 8));
    public static final List<Position> INITIAL_POSITIONS_RED = List.of(
            new Position(3, 2),
            new Position(3, 8));

    public Cannon(final Team team) {
        super("포", team);
    }

    @Override
    public Consumer<Pieces> getMovableValidator(final Position beforePosition,
                                                final Position afterPosition) {
        return pieces -> {
            CommonValidator.validateStraightMovement(beforePosition, afterPosition);
            validateNoSameTeamPieceAt(afterPosition, team, pieces);
            validateDestinationNotCannon(pieces, afterPosition);
            validateCannonJumpRule(pieces, beforePosition, afterPosition);
        };
    }

    @Override
    public Consumer<Pieces> getPalaceMovableValidator(final Position beforePosition, final Position afterPosition) {
        return pieces -> {
            CommonValidator.validateLinearMovement(beforePosition, afterPosition);
            validateNoSameTeamPieceAt(afterPosition, team, pieces);
            validateDestinationNotCannon(pieces, afterPosition);
            validatePalaceCannonJumpRule(pieces, beforePosition, afterPosition);
        };
    }

    private void validateDestinationNotCannon(final Pieces pieces, final Position afterPosition) {
        if (pieces.get(afterPosition).isCannon()) {
            throw new IllegalArgumentException("불가능한 이동입니다.");
        }
    }

    private void validateCannonJumpRule(
            final Pieces pieces,
            final Position beforePosition,
            final Position afterPosition) {

        int obstaclesCount = countObstaclesOnPath(pieces, beforePosition, afterPosition);

        if (obstaclesCount != 1) {
            throw new IllegalArgumentException("포는 반드시 하나의 장애물을 넘어야 합니다.");
        }
    }

    private int countObstaclesOnPath(
            final Pieces pieces,
            final Position beforePosition,
            final Position afterPosition) {

        Movement movement = afterPosition.getMovementTo(beforePosition);
        Movement nextMovement = Movement.findStraightUnitMovement(movement.x(), movement.y());

        int obstaclesCount = 0;
        Position currentPosition = beforePosition.plus(nextMovement.x(), nextMovement.y());

        while (!currentPosition.equals(afterPosition)) {
            validateNoCannonOnPath(pieces, currentPosition);
            if (!pieces.get(currentPosition).isNone()) {
                obstaclesCount++;
            }
            currentPosition = currentPosition.plus(nextMovement.x(), nextMovement.y());
        }
        return obstaclesCount;
    }

    private void validatePalaceCannonJumpRule(
            final Pieces pieces,
            final Position beforePosition,
            final Position afterPosition) {

        int obstaclesCount = countObstaclesOnPalacePath(pieces, beforePosition, afterPosition);

        if (obstaclesCount != 1) {
            throw new IllegalArgumentException("포는 반드시 하나의 장애물을 넘어야 합니다.");
        }
    }

    private int countObstaclesOnPalacePath(
            final Pieces pieces,
            final Position beforePosition,
            final Position afterPosition) {

        Movement movement = afterPosition.getMovementTo(beforePosition);
        Movement nextMovement = Movement.findUnitMovement(movement.x(), movement.y());

        int obstaclesCount = 0;
        Position currentPosition = beforePosition.plus(nextMovement.x(), nextMovement.y());

        while (!currentPosition.equals(afterPosition)) {
            validateNoCannonOnPath(pieces, currentPosition);
            if (!pieces.get(currentPosition).isNone()) {
                obstaclesCount++;
            }
            currentPosition = currentPosition.plus(nextMovement.x(), nextMovement.y());
        }
        return obstaclesCount;
    }

    private void validateNoCannonOnPath(final Pieces pieces, final Position position) {
        if (pieces.get(position).isCannon()) {
            throw new IllegalArgumentException("불가능한 이동입니다");
        }
    }

    @Override
    public boolean isCannon() {
        return true;
    }

    @Override
    public int getScore() {
        return 7;
    }
}