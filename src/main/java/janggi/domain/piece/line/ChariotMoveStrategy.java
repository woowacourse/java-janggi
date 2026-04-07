package janggi.domain.piece.line;

import janggi.domain.board.BoardSnapshot;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.MoveStrategy;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.List;

public final class ChariotMoveStrategy extends LineMoveStrategy {

    private static final MoveStrategy INSTANCE = new ChariotMoveStrategy();

    public static MoveStrategy instance() {
        return INSTANCE;
    }

    @Override
    protected List<Position> findPlaceablePositionsByDirection(
            BoardSnapshot board,
            Position from,
            Dynasty dynasty,
            Direction direction
    ) {
        List<Position> allPositions = from.findAllPositionsByDirection(direction);
        List<Position> positions = board.selectUntilNearestPiecePosition(allPositions);

        if (positions.isEmpty()) {
            return positions;
        }
        return removeLastIfSameDynasty(board, dynasty, positions);
    }

}
