package janggi.domain.piece;

import janggi.domain.piece.movement.Movement;

import java.util.List;
import java.util.Map;

public class General extends PathMovingPiece {
    private static final Position INITIAL_POSITIONS_BLUE = new Position(9, 5);
    private static final Position INITIAL_POSITIONS_RED = new Position(2, 5);

    public General(final Position position, final Team team) {
        super("궁", position, team);
    }

    public static List<Piece> createWithInitialPositions(final Team team) {
        if (team.equals(Team.BLUE)) {
            return List.of(new General(INITIAL_POSITIONS_BLUE, team));
        }
        return List.of(new General(INITIAL_POSITIONS_RED, team));
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
        return new General(position, team);
    }
}
