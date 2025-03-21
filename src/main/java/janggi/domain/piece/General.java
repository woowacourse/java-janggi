package janggi.domain.piece;

import java.util.List;
import java.util.Map;

public class General extends Piece {
    private static final Position INITIAL_POSITIONS_BLUE = new Position(9, 5);
    private static final Position INITIAL_POSITIONS_RED = new Position(2, 5);

    public General(final Position position, final Team team) {
        super("궁", position, team);
    }

    public static List<Piece> createWithInitialPositions(final Team team) {
        if (team.equals(Team.BLUE)) {
            return List.of(new General(INITIAL_POSITIONS_BLUE, team));
        }
        return List.of(new General(INITIAL_POSITIONS_RED, team));
    }

    @Override
    public General move(final Map<Position, Piece> pieces, final Position positionToMove) {
        validateIsPositionMovable(positionToMove);
        validateIsSameTeamNotInPositionToMove(pieces, positionToMove);
        return new General(positionToMove, team);
    }

    private void validateIsPositionMovable(final Position value) {
        if (checkIsPositionNotMovable(value)) {
            throw new IllegalArgumentException("불가능한 이동입니다.");
        }
    }

    private boolean checkIsPositionNotMovable(final Position value) {
        return Math.abs(value.x() - getPosition().x()) + Math.abs(value.y() - getPosition().y()) != 1;
    }
}
