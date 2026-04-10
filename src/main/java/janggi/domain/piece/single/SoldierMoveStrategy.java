package janggi.domain.piece.single;

import janggi.domain.board.BoardSnapshot;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.MoveStrategy;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;

public final class SoldierMoveStrategy extends SingleMoveStrategy {

    private static final MoveStrategy SOLDIER_MOVE_STRATEGY = new SoldierMoveStrategy();

    public static MoveStrategy instance() {
        return SOLDIER_MOVE_STRATEGY;
    }

    @Override
    protected boolean isMovableDirection(Direction direction, Dynasty dynasty) {
        return !direction.equals(dynasty.front().back());
    }

    @Override
    protected boolean isPlaceable(BoardSnapshot board, Position to, Dynasty dynasty) {
        return board.isPlaceable(to, dynasty);
    }

}
