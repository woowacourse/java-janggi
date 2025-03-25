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
    public Consumer<Map<Position, Piece>> getMovableValidator(final Position beforePosition,
                                                              final Position afterPosition) {
        return board -> {
            validateIsSameTeamNotInPositionToMove(board, afterPosition);
            validateIsPositionMovable(beforePosition, afterPosition);
        };
    }

    private void validateIsPositionMovable(final Position beforePosition, final Position afterPosition) {
        if (checkDistanceIsOne(beforePosition, afterPosition)) {
            throw new IllegalArgumentException("불가능한 이동입니다.");
        }
    }

    private boolean checkDistanceIsOne(final Position beforePosition, final Position afterPosition) {
        return Math.abs(afterPosition.x() - beforePosition.x()) + Math.abs(afterPosition.y() - beforePosition.y()) != 1;
    }

}
