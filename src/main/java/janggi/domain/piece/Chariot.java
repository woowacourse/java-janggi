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
        if (pieces.get(positionToMove).getTeamType().equals(teamType)) {
            throw new IllegalArgumentException();
        }
    }

    private void validateNothingBetweenPositionToMove(Map<Position, Piece> pieces, Position positionToMove) {
        Distance distance = Distance.getDistance(
                positionToMove.x() - getPosition().x(),
                positionToMove.y() - getPosition().y()
        );

        for (Position position = getPosition().plus(distance.x(), distance.y()); !position.equals(positionToMove); position = position.plus(distance.x(), distance.y())) {
            if (None.isNotNone(pieces.get(position))) {
                throw new IllegalArgumentException();
            }
        }
    }
}
