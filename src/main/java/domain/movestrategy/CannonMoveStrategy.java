package domain.movestrategy;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Delta;
import domain.piece.Piece;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CannonMoveStrategy extends BasicMoveStrategy {

    @Override
    public List<Position> getMovablePositions(Board board, Position from) {
        Piece cannon = board.getPieceAt(from);
        List<Position> movable = new ArrayList<>();

        for (Delta delta : Delta.ORTHOGONAL_DELTAS) {
            collectMovablePositions(board, cannon, from, delta, movable);
        }

        return movable;
    }

    private void collectMovablePositions(
            Board board,
            Piece cannon,
            Position from,
            Delta direction,
            List<Position> movable
    ) {
        Optional<Position> bridge = findBridge(board, from, direction);
        if (bridge.isEmpty()) {
            return;
        }

        collectLandingPositions(board, cannon, bridge.get(), direction, movable);
    }

    private Optional<Position> findBridge(Board board, Position from, Delta direction) {
        Position current = from;

        while (current.canMove(direction)) {
            current = current.move(direction);

            if (board.isEmpty(current)) {
                continue;
            }

            Piece piece = board.getPieceAt(current);
            if (!isBridge(piece)) {
                return Optional.empty();
            }

            return Optional.of(current);
        }

        return Optional.empty();
    }

    private void collectLandingPositions(
            Board board,
            Piece cannon,
            Position bridge,
            Delta direction,
            List<Position> movable
    ) {
        Position current = bridge;

        while (current.canMove(direction)) {
            current = current.move(direction);

            if (board.isEmpty(current)) {
                movable.add(current);
                continue;
            }

            Piece target = board.getPieceAt(current);
            if (canCapture(cannon, target)) {
                movable.add(current);
            }
            return;
        }
    }

    private boolean isBridge(Piece piece) {
        return !piece.isCannon();
    }

    private boolean canCapture(Piece cannon, Piece target) {
        return !target.isCannon() && cannon.isOpposite(target);
    }
}
