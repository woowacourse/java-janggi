package janggi.domain.piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Soldier extends Piece {
    private static final List<Position> INITIAL_POSITIONS_BLUE = List.of(
            new Position(7, 1),
            new Position(7, 3),
            new Position(7, 5),
            new Position(7, 7),
            new Position(7, 9)
    );
    private static final List<Position> INITIAL_POSITIONS_RED = List.of(
            new Position(4, 1),
            new Position(4, 3),
            new Position(4, 5),
            new Position(4, 7),
            new Position(4, 9)
    );

    public Soldier(final Position position, final Team team) {
        super("졸", position, team);
    }

    public static List<Piece> createWithInitialPositions(final Team team) {
        List<Piece> soldiers = new ArrayList<>();
        if (team.equals(Team.BLUE)) {
            INITIAL_POSITIONS_BLUE.forEach(position ->
                    soldiers.add(new Soldier(position, team)));
            return soldiers;
        }
        INITIAL_POSITIONS_RED.forEach(position ->
                soldiers.add(new Soldier(position, team)));
        return soldiers;
    }

    public Soldier move(final Map<Position, Piece> pieces, final Position positionToMove) {
        validateIsSameTeamNotInPositionToMove(pieces, positionToMove);
        validateIsPositionMovable(positionToMove);
        return new Soldier(positionToMove, team);
    }

    private void validateIsPositionMovable(final Position value) {
        if (checkIsDirectionNotMovable(value) || checkIsPositionNotMovable(value)) {
            throw new IllegalArgumentException("불가능한 이동입니다.");
        }
    }

    private boolean checkIsDirectionNotMovable(final Position value) {
        if (team == Team.BLUE) {
            return value.x() - getPosition().x() > 0;
        }
        return value.x() - getPosition().x() < 0;
    }

    private boolean checkIsPositionNotMovable(final Position value) {
        return Math.abs(value.x() - getPosition().x()) + Math.abs(value.y() - getPosition().y()) != 1;
    }
}
