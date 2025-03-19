package janggi.domain.piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Chariot extends Piece {
    private static final List<Position> INITIAL_POSITIONS_BLUE = List.of(
            new Position(10, 1),
            new Position(10, 9));
    private static final List<Position> INITIAL_POSITIONS_RED = List.of(
            new Position(1, 1),
            new Position(1, 9));

    public Chariot(final Position position, final TeamType teamType) {
        super("차", position, teamType);
    }

    public static List<Piece> createWithInitialPositions(final TeamType teamType) {
        List<Piece> chariots = new ArrayList<>();
        if (teamType.equals(TeamType.BLUE)) {
            INITIAL_POSITIONS_BLUE.forEach(position ->
                    chariots.add(new Chariot(position, teamType)));
            return chariots;
        }
        INITIAL_POSITIONS_RED.forEach(position ->
                chariots.add(new Chariot(position, teamType)));
        return chariots;
    }

    public Chariot move(final Map<Position, Piece> pieces, final Position positionToMove) {
        validateIsPositionMovable(positionToMove);
        validateIsSameTeamNotInPositionToMove(pieces, positionToMove);
        validateNothingBetweenPositionToMove(pieces, positionToMove);
        return new Chariot(positionToMove, teamType);
    }

    private void validateIsPositionMovable(final Position value) {
        if (checkIsPositionNotDiagonal(value)) {
            throw new IllegalArgumentException();
        }
    }

    private boolean checkIsPositionNotDiagonal(final Position value) {
        return Math.abs(value.x() - getPosition().x()) != 0 && Math.abs(value.y() - getPosition().y()) != 0;
    }

    private void validateIsSameTeamNotInPositionToMove(Map<Position, Piece> pieces, Position positionToMove) {
        if (pieces.containsKey(positionToMove)) {
            throw new IllegalArgumentException();
        }
    }

    private void validateNothingBetweenPositionToMove(Map<Position, Piece> pieces, Position positionToMove) {
        if (pieces.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(this.getPosition()) && !entry.getKey().equals(positionToMove))
                .anyMatch(entry -> !(entry.getValue() instanceof None))) {
            throw new IllegalArgumentException();
        }
    }
}
