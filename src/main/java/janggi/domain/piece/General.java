package janggi.domain.piece;

import java.util.List;
import java.util.Map;

public class General extends Piece {
    private static final Position INITIAL_POSITIONS_BLUE = new Position(9, 5);
    private static final Position INITIAL_POSITIONS_RED = new Position(2, 5);

    public General(final Position position, final TeamType teamType) {
        super("궁", position, teamType);
    }

    public static List<Piece> createWithInitialPositions(final TeamType teamType) {
        if (teamType.equals(TeamType.BLUE)) {
            return List.of(new General(INITIAL_POSITIONS_BLUE, teamType));
        }
        return List.of(new General(INITIAL_POSITIONS_RED, teamType));
    }

    public General move(final Map<Position, Piece> pieces, final Position positionToMove) {
        validateIsPositionMovable(positionToMove);
        validateIsSameTeamNotInPositionToMove(pieces, positionToMove);
        return new General(positionToMove, teamType);
    }

    private void validateIsPositionMovable(final Position value) {
        if (checkIsPositionNotMovable(value)) {
            throw new IllegalArgumentException();
        }
    }

    private boolean checkIsPositionNotMovable(final Position value) {
        return Math.abs(value.x() - getPosition().x()) + Math.abs(value.y() - getPosition().y()) != 1;
    }

    private void validateIsSameTeamNotInPositionToMove(Map<Position, Piece> pieces, Position positionToMove) {
        if(pieces.containsKey(positionToMove)){
            throw new IllegalArgumentException();
        }
    }
}
