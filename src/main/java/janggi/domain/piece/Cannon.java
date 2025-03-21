package janggi.domain.piece;

import janggi.domain.piece.movement.Movement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cannon extends Piece {
    private static final List<Position> INITIAL_POSITIONS_BLUE = List.of(
            new Position(8, 2),
            new Position(8, 8));
    private static final List<Position> INITIAL_POSITIONS_RED = List.of(
            new Position(3, 2),
            new Position(3, 8));

    public Cannon(final Position position, final Team team) {
        super("포", position, team);
    }

    public static List<Piece> createWithInitialPositions(Team team) {
        List<Piece> cannons = new ArrayList<>();
        if (team.equals(Team.BLUE)) {
            INITIAL_POSITIONS_BLUE.forEach(position ->
                    cannons.add(new Cannon(position, team)));
            return cannons;
        }
        INITIAL_POSITIONS_RED.forEach(position ->
                cannons.add(new Cannon(position, team)));
        return cannons;
    }

    @Override
    public Cannon move(final Map<Position, Piece> pieces, final Position positionToMove) {
        validateIsPositionMovable(positionToMove);
        validateIsSameTeamNotInPositionToMove(pieces, positionToMove);
        validateNotCannonInPositionToMove(pieces, positionToMove);
        validateOneNotCannonBetweenPositionToMove(pieces, positionToMove);
        return new Cannon(positionToMove, team);
    }

    private void validateIsPositionMovable(final Position value) {
        if (checkIsPositionNotDiagonal(value)) {
            throw new IllegalArgumentException("불가능한 이동입니다.");
        }
    }

    private void validateNotCannonInPositionToMove(Map<Position, Piece> pieces, Position positionToMove) {
        if (pieces.get(positionToMove) instanceof Cannon) {
            throw new IllegalArgumentException("불가능한 이동입니다.");
        }
    }

    private void validateOneNotCannonBetweenPositionToMove(Map<Position, Piece> pieces, Position positionToMove) {
        Movement movement = Movement.getDistance(
                positionToMove.x() - getPosition().x(),
                positionToMove.y() - getPosition().y()
        );
        int count = 0;
        for (Position position = getPosition().plus(movement.x(), movement.y());
             !position.equals(positionToMove);
             position = position.plus(movement.x(), movement.y())
        ) {
            isCannonOnMovement(pieces, position);
            if (None.isNotNone(pieces.get(position))) {
                count++;
            }
        }

        if (count != 1) {
            throw new IllegalArgumentException("불가능한 이동입니다");
        }
    }

    private static void isCannonOnMovement(final Map<Position, Piece> pieces, final Position position) {
        if (pieces.get(position) instanceof Cannon) {
            throw new IllegalArgumentException("불가능한 이동입니다");
        }
    }

    private boolean checkIsPositionNotDiagonal(final Position value) {
        return Math.abs(value.x() - getPosition().x()) != 0 && Math.abs(value.y() - getPosition().y()) != 0;
    }
}
