package janggi.domain.piece.line;

import janggi.domain.board.BoardSnapshot;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.MoveStrategy;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public abstract class LineMoveStrategy implements MoveStrategy {

    @Override
    public List<Position> findPlaceablePositions(BoardSnapshot board, Position from, Dynasty dynasty) {
        List<Position> placeablePositions = new ArrayList<>();
        for (Direction direction : from.directions()) {
            placeablePositions.addAll(findPlaceablePositionsByDirection(board, from, dynasty, direction));
        }
        return placeablePositions;
    }

    protected abstract List<Position> findPlaceablePositionsByDirection(
            BoardSnapshot board,
            Position from,
            Dynasty dynasty,
            Direction direction
    );

    protected List<Position> removeLastIfSameDynasty(BoardSnapshot board, Dynasty dynasty, List<Position> positions) {
        if (positions.isEmpty()) {
            return positions;
        }

        List<Position> result = new ArrayList<>(positions);
        Position last = result.getLast();
        if (board.isSameDynasty(last, dynasty)) {
            result.removeLast();
        }
        return result;
    }

}
