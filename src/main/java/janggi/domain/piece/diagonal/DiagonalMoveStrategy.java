package janggi.domain.piece.diagonal;

import janggi.domain.board.BoardSnapshot;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.MoveStrategy;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class DiagonalMoveStrategy implements MoveStrategy {

    @Override
    public List<Position> findPlaceablePositions(BoardSnapshot board, Position from, Dynasty dynasty) {
        List<Position> placeablePositions = new ArrayList<>();
        for (Direction direction : Direction.valuesFourDirection()) {
            from.nextPositionByDirection(direction)
                    .filter(board::isEmpty)
                    .ifPresent(first -> addPlaceablePositions(
                            board, dynasty, placeablePositions, first, direction.next(), direction.prev())
                    );
        }
        return placeablePositions;
    }

    private void addPlaceablePositions(
            BoardSnapshot board,
            Dynasty dynasty,
            List<Position> placeablePositions,
            Position first,
            Direction left,
            Direction right
    ) {
        findDestination(first, left, board)
                .filter(position -> board.isPlaceable(position, dynasty))
                .ifPresent(placeablePositions::add);

        findDestination(first, right, board)
                .filter(position -> board.isPlaceable(position, dynasty))
                .ifPresent(placeablePositions::add);
    }

    protected abstract Optional<Position> findDestination(Position from, Direction direction, BoardSnapshot board);

}
