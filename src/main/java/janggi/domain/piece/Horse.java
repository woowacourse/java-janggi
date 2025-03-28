package janggi.domain.piece;

import janggi.domain.piece.movement.HorsePathMovement;
import janggi.domain.piece.movement.Movement;
import java.util.List;
import java.util.function.Consumer;

public class Horse extends Piece {
    private static final List<Position> INITIAL_POSITIONS_BLUE_LEFT = List.of(
            new Position(10, 2),
            new Position(10, 3)
    );
    private static final List<Position> INITIAL_POSITIONS_BLUE_RIGHT = List.of(
            new Position(10, 7),
            new Position(10, 8)
    );
    private static final List<Position> INITIAL_POSITIONS_RED_LEFT = List.of(
            new Position(1, 2),
            new Position(1, 3)
    );
    private static final List<Position> INITIAL_POSITIONS_RED_RIGHT = List.of(
            new Position(1, 7),
            new Position(1, 8)
    );

    public Horse(final Team team) {
        super("마", team);
    }

    public static List<Position> getInitialPositions(
            final Team team,
            final HorseSide leftHorsePosition,
            final HorseSide rightHorsePosition) {
        if (team.equals(Team.BLUE)) {
            return getBlueInitialPositions(leftHorsePosition, rightHorsePosition);
        }
        return getRedInitialPositions(leftHorsePosition, rightHorsePosition);
    }

    private static List<Position> getBlueInitialPositions(
            final HorseSide leftHorsePosition,
            final HorseSide rightHorsePosition
    ) {
        return List.of(
                INITIAL_POSITIONS_BLUE_LEFT.get(leftHorsePosition.value()),
                INITIAL_POSITIONS_BLUE_RIGHT.get(rightHorsePosition.value()));
    }

    private static List<Position> getRedInitialPositions(
            final HorseSide leftHorsePosition,
            final HorseSide rightHorsePosition
    ) {
        return List.of(
                INITIAL_POSITIONS_RED_LEFT.get(leftHorsePosition.value()),
                INITIAL_POSITIONS_RED_RIGHT.get(rightHorsePosition.value()));
    }

    private void validateNoObstaclesOnPath(final Pieces pieces, final Position beforePosition,
                                           final List<Movement> pathMovements) {
        boolean hasObstacle = pathMovements.stream()
                .map(routeDistance -> beforePosition.plus(routeDistance.x(), routeDistance.y()))
                .anyMatch(position -> !pieces.get(position).isNone());

        if (hasObstacle) {
            throw new IllegalArgumentException("불가능한 이동입니다.");
        }
    }

    @Override
    public Consumer<Pieces> getMovableValidator(final Position beforePosition,
                                                final Position afterPosition) {
        return pieces -> {
            validateNoSameTeamPieceAt(afterPosition, team, pieces);
            validateNoObstaclesOnPath(pieces, beforePosition,
                    HorsePathMovement.findPathMovements(beforePosition, afterPosition));
        };
    }
}