package janggi.domain.piece;

import java.util.List;
import java.util.function.Consumer;

public class Guard extends Piece {
    public static final List<Position> INITIAL_POSITIONS_BLUE = List.of(new Position(10, 4), new Position(10, 6));
    public static final List<Position> INITIAL_POSITIONS_RED = List.of(new Position(1, 4), new Position(1, 6));

    public Guard(final Team team) {
        super("사", team);
    }

    @Override
    public Consumer<Pieces> getMovableValidator(final Position beforePosition, final Position afterPosition) {
        return pieces -> {
            CommonValidator.validateSingleStepMovement(beforePosition, afterPosition);
            validateNoSameTeamPieceAt(afterPosition, team, pieces);
        };
    }
}