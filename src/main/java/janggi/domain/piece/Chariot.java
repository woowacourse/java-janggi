package janggi.domain.piece;

import janggi.domain.piece.movement.Movement;
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

    public Chariot(final Position position, final Team team) {
        super("차", position, team);
    }

    public static List<Piece> createWithInitialPositions(final Team team) {
        List<Piece> chariots = new ArrayList<>();
        if (team.equals(Team.BLUE)) {
            INITIAL_POSITIONS_BLUE.forEach(position ->
                    chariots.add(new Chariot(position, team)));
            return chariots;
        }
        INITIAL_POSITIONS_RED.forEach(position ->
                chariots.add(new Chariot(position, team)));
        return chariots;
    }

    public Chariot move(final Map<Position, Piece> pieces, final Position positionToMove) {
        validateIsPositionMovable(positionToMove);
        validateIsSameTeamNotInPositionToMove(pieces, positionToMove);
        validateNothingBetweenPositionToMove(pieces, positionToMove);
        return new Chariot(positionToMove, team);
    }

    private void validateIsPositionMovable(final Position value) {
        if (checkIsPositionNotDiagonal(value)) {
            throw new IllegalArgumentException("불가능한 이동입니다.");
        }
    }

    private void validateNothingBetweenPositionToMove(Map<Position, Piece> pieces, Position positionToMove) {
        Movement movement = Movement.getDistance(
                positionToMove.x() - getPosition().x(),
                positionToMove.y() - getPosition().y()
        );

        for (Position position = getPosition().plus(movement.x(), movement.y()); !position.equals(positionToMove); position = position.plus(
                movement.x(), movement.y())) {
            if (None.isNotNone(pieces.get(position))) {
                throw new IllegalArgumentException("불가능한 이동입니다");
            }
        }
    }

    private boolean checkIsPositionNotDiagonal(final Position value) {
        return Math.abs(value.x() - getPosition().x()) != 0 && Math.abs(value.y() - getPosition().y()) != 0;
    }
}
