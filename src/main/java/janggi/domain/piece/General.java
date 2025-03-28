package janggi.domain.piece;

import java.util.List;
import java.util.function.Consumer;

public class General extends Piece {
    public static final List<Position> INITIAL_POSITIONS_BLUE = List.of(new Position(9, 5));
    public static final List<Position> INITIAL_POSITIONS_RED = List.of(new Position(2, 5));

    public General(final Team team) {
        super("궁", team);
    }

    @Override
    public Consumer<Pieces> getMovableValidator(final Position beforePosition, final Position afterPosition) {
        return pieces -> {
            CommonValidator.validateSingleStepMovement(beforePosition, afterPosition);
            validateNoSameTeamPieceAt(afterPosition, team, pieces);
        };
    }
}