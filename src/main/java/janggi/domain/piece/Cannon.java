package janggi.domain.piece;

import janggi.domain.piece.movement.Movement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cannon extends StraightMovingPiece {
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
    protected boolean checkPieceCondition(Map<Position, Piece> pieces, Position positionToMove, Movement direction) {
        Position currentPosition = getPosition();
        int count = 0;
        while(currentPosition.isNotEndPoint() || currentPosition.equals(positionToMove)) {
            currentPosition = currentPosition.plus(direction.getX(), direction.getY());
            if(pieces.get(currentPosition).isNotNone()) {
                count ++;
            }
            if(pieces.get(currentPosition) instanceof Cannon) {
                return false;
            }
        }
        return count == 1;
    }
    @Override
    public Piece from(Position position) {
        return new Cannon(position, getTeam());
    }
}
