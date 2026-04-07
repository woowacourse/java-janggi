package janggi.domain.piece.single;

import janggi.domain.board.BoardSnapshot;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.MoveStrategy;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.List;
import java.util.Optional;

public abstract class SingleMoveStrategy implements MoveStrategy {

    @Override
    public List<Position> findPlaceablePositions(BoardSnapshot board, Position from, Dynasty dynasty) {
        return from.directions().stream()
                .filter(direction -> isMovableDirection(direction, dynasty))
                .map(from::nextPositionByDirection)
                .flatMap(Optional::stream)
                .filter(to -> isPlaceable(board, to, dynasty))
                .toList();
    }

    protected abstract boolean isMovableDirection(Direction direction, Dynasty dynasty);

    protected abstract boolean isPlaceable(BoardSnapshot board, Position to, Dynasty dynasty);

}
