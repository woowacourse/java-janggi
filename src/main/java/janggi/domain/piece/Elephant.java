package janggi.domain.piece;

import janggi.domain.piece.movement.ElephantPathMovement;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Elephant extends Piece {
    private static final List<Position> INITIAL_POSITIONS_BLUE_LEFT = List.of(
            new Position(10, 3),
            new Position(10, 2)
    );
    private static final List<Position> INITIAL_POSITIONS_BLUE_RIGHT = List.of(
            new Position(10, 8),
            new Position(10, 7)
    );
    private static final List<Position> INITIAL_POSITIONS_RED_LEFT = List.of(
            new Position(1, 3),
            new Position(1, 2)
    );
    private static final List<Position> INITIAL_POSITIONS_RED_RIGHT = List.of(
            new Position(1, 8),
            new Position(1, 7)
    );

    public Elephant(final Team team) {
        super("상", team);
    }

    public static List<Position> getInitialPositions(final Team team, final HorseSide leftHorsePosition,
                                                     final HorseSide rightHorsePosition) {
        if (team.equals(Team.BLUE)) {
            return getBlueInitialPositions(leftHorsePosition, rightHorsePosition);
        }
        return getRedInitialPositions(leftHorsePosition, rightHorsePosition);
    }

    private static List<Position> getBlueInitialPositions(final HorseSide leftHorsePosition,
                                                          final HorseSide rightHorsePosition) {
        return List.of(
                INITIAL_POSITIONS_BLUE_LEFT.get(leftHorsePosition.value()),
                INITIAL_POSITIONS_BLUE_RIGHT.get(rightHorsePosition.value()));
    }

    private static List<Position> getRedInitialPositions(final HorseSide leftHorsePosition,
                                                         final HorseSide rightHorsePosition) {
        return List.of(
                INITIAL_POSITIONS_RED_LEFT.get(leftHorsePosition.value()),
                INITIAL_POSITIONS_RED_RIGHT.get(rightHorsePosition.value()));
    }

    @Override
    public Consumer<Map<Position, Piece>> getMovableValidator(final Position beforePosition,
                                                              final Position afterPosition) {
        return board -> {
            Validator.validateNoSameTeamPieceAt(team, board, afterPosition);
            Validator.validateNoObstaclesOnPath(board, beforePosition,
                    ElephantPathMovement.findPathMovements(beforePosition, afterPosition));
        };
    }
}