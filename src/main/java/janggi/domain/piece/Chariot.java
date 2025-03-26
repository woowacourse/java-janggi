package janggi.domain.piece;

import janggi.domain.piece.movement.Movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Chariot extends StraightMovingPiece {
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

    @Override
    protected boolean checkPieceCondition(Map<Position, Piece> pieces, Position positionToMove, Movement direction) {
        Position currentPosition = getPosition();
        while (currentPosition.isNotEndPoint() || currentPosition.equals(positionToMove)) {
            if (pieces.get(currentPosition).isNotNone()) {
                throw new IllegalArgumentException("불가능한 이동입니다");
            }
            currentPosition = currentPosition.plus(direction.getX(), direction.getY());
        }
        return true;
    }

    @Override
    public Piece from(Position position) {
        return new Chariot(position, getTeam());
    }
}
