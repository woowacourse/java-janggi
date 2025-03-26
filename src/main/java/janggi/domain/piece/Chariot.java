package janggi.domain.piece;

import java.util.List;
import java.util.Map;
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
    public Consumer<Map<Position, Piece>> getMovableValidator(final Position beforePosition,
                                                              final Position afterPosition) {
        return board -> {
            Validator.validateNoSameTeamPieceAt(team, board, afterPosition);
            Validator.validateStraightMovement(beforePosition, afterPosition);
            Validator.validateNoObstaclesOnPath(board, beforePosition, afterPosition);
        };
    }
}