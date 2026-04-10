package janggi.domain.piece.diagonal;

import janggi.domain.board.BoardSnapshot;
import janggi.domain.piece.MoveStrategy;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.Optional;

public final class HorseMoveStrategy extends DiagonalMoveStrategy {

    private static final MoveStrategy HORSE_MOVE_STRATEGY = new HorseMoveStrategy();

    public static MoveStrategy instance() {
        return HORSE_MOVE_STRATEGY;
    }

    @Override
    protected Optional<Position> findDestination(Position from, Direction direction, BoardSnapshot board) {
        return from.nextPositionByDirection(direction);
    }

}
