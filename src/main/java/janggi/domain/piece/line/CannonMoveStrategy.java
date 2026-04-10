package janggi.domain.piece.line;

import static janggi.domain.piece.PieceType.CANNON;

import janggi.domain.board.BoardSnapshot;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.MoveStrategy;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public final class CannonMoveStrategy extends LineMoveStrategy {

    private static final MoveStrategy CANNON_MOVE_STRATEGY = new CannonMoveStrategy();

    public static MoveStrategy instance() {
        return CANNON_MOVE_STRATEGY;
    }

    @Override
    protected List<Position> findPlaceablePositionsByDirection(
            BoardSnapshot board,
            Position from,
            Dynasty dynasty,
            Direction direction
    ) {
        List<Position> beforeTarget = board.selectUntilNearestPiecePosition(
                from.findAllPositionsByDirection(direction)
        );
        if (beforeTarget.isEmpty()) {
            return List.of();
        }

        Position jumpTarget = beforeTarget.getLast();
        if (board.isSamePieceType(jumpTarget, CANNON)) {
            return List.of();
        }

        List<Position> afterTarget = board.selectUntilNearestPiecePosition(
                jumpTarget.findAllPositionsByDirection(direction)
        );
        if (afterTarget.isEmpty()) {
            return List.of();
        }

        return removeLastIfCannotCatch(board, dynasty, afterTarget);
    }

    private List<Position> removeLastIfCannotCatch(BoardSnapshot board, Dynasty dynasty, List<Position> positions) {
        List<Position> result = new ArrayList<>(positions);
        Position last = result.getLast();

        if (board.isSamePieceType(last, CANNON) || board.isSameDynasty(last, dynasty)) {
            result.removeLast();
        }
        return result;
    }

}
