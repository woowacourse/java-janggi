package janggi.domain.piece;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class General extends Piece {
    public static final List<Position> INITIAL_POSITIONS_BLUE = List.of(new Position(9, 5));
    public static final List<Position> INITIAL_POSITIONS_RED = List.of(new Position(2, 5));

    public General(final Team team) {
        super("궁", team);
    }

    @Override
    public Consumer<Map<Position, Piece>> getMovableValidator(final Position beforePosition, final Position afterPosition) {
        return board -> {
            Validator.validateNoSameTeamPieceAt(team, board, afterPosition);
            Validator.validateSingleStepMovement(beforePosition, afterPosition);
        };
    }
}