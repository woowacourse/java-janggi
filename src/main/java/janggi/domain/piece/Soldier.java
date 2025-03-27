package janggi.domain.piece;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Soldier extends Piece {
    public static final List<Position> INITIAL_POSITIONS_BLUE = List.of(
            new Position(7, 1),
            new Position(7, 3),
            new Position(7, 5),
            new Position(7, 7),
            new Position(7, 9)
    );
    public static final List<Position> INITIAL_POSITIONS_RED = List.of(
            new Position(4, 1),
            new Position(4, 3),
            new Position(4, 5),
            new Position(4, 7),
            new Position(4, 9)
    );

    public Soldier(final Team team) {
        super("졸", team);
    }

    @Override
    public Consumer<Map<Position, Piece>> getMovableValidator(
            final Position beforePosition,
            final Position afterPosition) {
        return board -> {
            Validator.validateNoSameTeamPieceAt(team, board, afterPosition);
            Validator.validateNotMovingTowardsOwnSide(team, beforePosition, afterPosition);
            Validator.validateSingleStepMovement(beforePosition, afterPosition);
        };
    }
}