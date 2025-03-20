package janggi.domain.piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Guard extends Piece {
    private static final List<Position> INITIAL_POSITIONS_BLUE = List.of(new Position(10, 4), new Position(10, 6));
    private static final List<Position> INITIAL_POSITIONS_RED = List.of(new Position(1, 4), new Position(1, 6));

    public Guard(final Position position, final Team team) {
        super("사", position, team);
    }

    public static List<Piece> createWithInitialPositions(final Team team) {
        List<Piece> guards = new ArrayList<>();
        if (team.equals(Team.BLUE)) {
            INITIAL_POSITIONS_BLUE.forEach(position ->
                    guards.add(new Guard(position, team)));
            return guards;
        }
        INITIAL_POSITIONS_RED.forEach(position ->
                guards.add(new Guard(position, team)));
        return guards;
    }

    public Guard move(final Map<Position, Piece> pieces, final Position positionToMove) {
        validateIsPositionMovable(positionToMove);
        validateIsSameTeamNotInPositionToMove(pieces, positionToMove);
        return new Guard(positionToMove, team);
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
