package janggi.domain.piece;

import janggi.domain.piece.movement.Movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Guard extends PathMovingPiece {
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

    @Override
    protected List<Movement> findMovements(Position positionToMove) {
        return List.of(Movement.getDiagonal(
                positionToMove.x() - getPosition().x(),
                positionToMove.y() - getPosition().y()
        ));
    }

    @Override
    protected boolean checkPieceCondition(Piece pieceInPositionToMove, Position checkingPosition) {
        return pieceInPositionToMove.isNone() &&
                checkingPosition.isPalace();
    }

    @Override
    public Piece from(Position position) {
        return new Guard(position, team);
    }
}
