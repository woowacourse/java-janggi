package janggi.domain.piece.diagonal;

import janggi.domain.board.BoardSnapshot;
import janggi.domain.piece.MoveStrategy;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.Optional;

public final class ElephantMoveStrategy extends DiagonalMoveStrategy {

    private static final MoveStrategy ELEPHANT_MOVE_STRATEGY = new ElephantMoveStrategy();

    public static MoveStrategy instance() {
        return ELEPHANT_MOVE_STRATEGY;
    }

    @Override
    protected Optional<Position> findDestination(Position from, Direction direction, BoardSnapshot board) {
        return from.nextPositionByDirection(direction)
                .filter(board::isEmpty)
                .flatMap(next -> next.nextPositionByDirection(direction));
    }

}
