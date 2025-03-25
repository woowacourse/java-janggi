package janggi.domain.piece;

import janggi.domain.piece.movement.HorseMovement;
import java.util.List;
import java.util.Map;
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
            Team team,
            HorseSide leftHorsePosition,
            HorseSide rightHorsePosition) {
        if (team.equals(Team.BLUE)) {
            return getBlueInitialPositions(leftHorsePosition, rightHorsePosition);
        }
        return getRedInitialPositions(leftHorsePosition, rightHorsePosition);
    }

    private static List<Position> getBlueInitialPositions(
            HorseSide leftHorsePosition,
            HorseSide rightHorsePosition
    ) {
        return List.of(
                INITIAL_POSITIONS_BLUE_LEFT.get(leftHorsePosition.value()),
                INITIAL_POSITIONS_BLUE_RIGHT.get(rightHorsePosition.value()));
    }

    private static List<Position> getRedInitialPositions(
            HorseSide leftHorsePosition,
            HorseSide rightHorsePosition
    ) {
        return List.of(
                INITIAL_POSITIONS_RED_LEFT.get(leftHorsePosition.value()),
                INITIAL_POSITIONS_RED_RIGHT.get(rightHorsePosition.value()));
    }

    @Override
    public Consumer<Map<Position, Piece>> getMovableValidator(final Position beforePosition,
                                                              final Position afterPosition) {
        return board -> {
            validateIsSameTeamNotInPositionToMove(board, afterPosition);
            validateNothingBetweenPositionToMove(board, beforePosition, afterPosition);
        };
    }

    private void validateNothingBetweenPositionToMove(final Map<Position, Piece> board, final Position beforePosition,
                                                      final Position afterPosition) {
        HorseMovement horseMovement = HorseMovement.getDirection(
                afterPosition.x() - beforePosition.x(),
                afterPosition.y() - beforePosition.y()
        );
        Position routePosition = beforePosition.plus(horseMovement.getRouteDistance().x(),
                horseMovement.getRouteDistance().y());

        if (!board.get(routePosition).isNone()) {
            throw new IllegalArgumentException("불가능한 이동입니다.");
        }
    }
}
