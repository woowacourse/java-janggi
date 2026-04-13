package domain.movestrategy;

import domain.board.Board;
import domain.board.Direction;
import domain.board.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CannonMoveStrategy implements MoveStrategy {

    @Override
    public List<Position> getMovablePositions(final Board board, final Position from) {
        List<Position> movable = new ArrayList<>();

        for (Direction direction : Direction.ORTHOGONAL_DIRECTIONS) {
            collectMovablePositions(board, from, direction, movable);
        }

        for (Direction direction : Direction.DIAGONAL_DIRECTIONS) {
            collectDiagonalMovablePositions(board, from, direction, movable);
        }

        return movable;
    }

    private void collectMovablePositions(
            final Board board,
            final Position from,
            final Direction direction,
            final List<Position> movable
    ) {
        Optional<Position> bridge = findBridge(board, from, direction);
        if (bridge.isEmpty()) {
            return;
        }

        collectLandingPositions(board, from, bridge.get(), direction, movable);
    }

    private Optional<Position> findBridge(final Board board, final Position from, final Direction direction) {
        Position current = from.move(direction);

        while (current.isInsideBoard() && board.isEmpty(current)) {
            current = current.move(direction);
        }

        if (!current.isInsideBoard() || board.isCannon(current)) {
            return Optional.empty();
        }

        return Optional.of(current);
    }

    private void collectLandingPositions(
            final Board board,
            final Position from,
            final Position bridge,
            final Direction direction,
            final List<Position> movable
    ) {
        Position current = bridge.move(direction);

        while (current.isInsideBoard() && board.isEmpty(current)) {
            movable.add(current);
            current = current.move(direction);
        }

        if (current.isInsideBoard() && board.isOpposite(from, current) && !board.isCannon(current)) {
            movable.add(current);
        }
    }

    private void collectDiagonalMovablePositions(
            final Board board,
            final Position from,
            final Direction direction,
            final List<Position> movable
    ) {
        Position bridge = from.move(direction);

        if (!bridge.isInsideBoard()
                || !from.isDiagonalConnected(bridge)
                || board.isEmpty(bridge)
                || board.isCannon(bridge)
        ) {
            return;
        }

        Position landing = bridge.move(direction);

        if (!landing.isInsideBoard() || !bridge.isDiagonalConnected(landing)) {
            return;
        }

        if (board.isEmpty(landing)) {
            movable.add(landing);
            return;
        }

        if (board.isOpposite(from, landing) && !board.isCannon(landing)) {
            movable.add(landing);
        }
    }
}
