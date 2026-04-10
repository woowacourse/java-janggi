package janggi.domain.piece.single;

import janggi.domain.board.BoardSnapshot;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.MoveStrategy;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;

public final class PalaceMoveStrategy extends SingleMoveStrategy {

    private static final MoveStrategy PALACE_MOVE_STRATEGY = new PalaceMoveStrategy();

    public static MoveStrategy instance() {
        return PALACE_MOVE_STRATEGY;
    }

    @Override
    protected boolean isMovableDirection(Direction direction, Dynasty dynasty) {
        return true;
    }

    @Override
    protected boolean isPlaceable(BoardSnapshot board, Position to, Dynasty dynasty) {
        return board.isPlaceable(to, dynasty) && to.isPalace(dynasty);
    }

}
