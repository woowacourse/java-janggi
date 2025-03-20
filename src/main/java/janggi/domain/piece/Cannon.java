package janggi.domain.piece;

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

    public Cannon(final Position position, final TeamType teamType) {
        super("포", position, teamType);
    }

    public Cannon move(final Map<Position, Piece> pieces, final Position positionToMove) {
        validateIsPositionMovable(positionToMove);
        validateIsSameTeamNotInPositionToMove(pieces, positionToMove);
        validateInNotCannonInPositionToMove(pieces, positionToMove);
        validateOneNotCannonBetweenPositionToMove(pieces, positionToMove);
        return new Cannon(positionToMove, teamType);
    }

    private void validateIsPositionMovable(final Position value) {
        if (checkIsPositionNotDiagonal(value)) {
            throw new IllegalArgumentException();
        }
    }

    private boolean checkIsPositionNotDiagonal(final Position value) {
        return Math.abs(value.x() - getPosition().x()) != 0 && Math.abs(value.y() - getPosition().y()) != 0;
    }

    private void validateInNotCannonInPositionToMove(Map<Position, Piece> pieces, Position positionToMove) {
        if (pieces.get(positionToMove) instanceof Cannon) {
            throw new IllegalArgumentException();
        }
    }

    private void validateIsSameTeamNotInPositionToMove(Map<Position, Piece> pieces, Position positionToMove) {
        if (pieces.get(positionToMove) instanceof None) {
            return;
        }
        throw new IllegalArgumentException();
    }

    private void validateOneNotCannonBetweenPositionToMove(Map<Position, Piece> pieces, Position positionToMove) {
        Position direction = CannonDirection.getDirection(
                new Position(
                        positionToMove.x() - getPosition().x(),
                        positionToMove.y() - getPosition().y()
                )
        );
        int count = 0;
        for(Position position = getPosition().plus(direction); !position.equals(positionToMove); position = position.plus(direction)) {
            if(pieces.get(position) instanceof Cannon) {
                throw new IllegalArgumentException();
            }
            if(!(pieces.get(position) instanceof None)) {
                count ++;
            }
        }

        if(count != 1) {
            throw new IllegalArgumentException();
        }
    }

    public static List<Piece> createWithInitialPositions(TeamType teamType) {
        List<Piece> cannons = new ArrayList<>();
        if (teamType.equals(TeamType.BLUE)) {
            INITIAL_POSITIONS_BLUE.forEach(position ->
                    cannons.add(new Cannon(position, teamType)));
            return cannons;
        }
        INITIAL_POSITIONS_RED.forEach(position ->
                cannons.add(new Cannon(position, teamType)));
        return cannons;
    }
}
