package domain.strategy;

import domain.Position;
import domain.Side;
import domain.board.BoardReader;
import domain.piece.Piece;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JumpStrategy implements MovementStrategy {
    private final List<Direction> defaultDirections;

    public JumpStrategy(List<Direction> defaultDirections) {
        this.defaultDirections = defaultDirections;
    }

    @Override
    public List<Path> generatePaths(Position current) {
        return defaultDirections.stream()
                .filter(current::canMove)
                .map(direction -> createPath(current, direction))
                .toList();
    }

    @Override
    public List<Position> getMovablePositions(Position current, BoardReader board, Side side) {
        return generatePaths(current).stream()
                .flatMap(path -> getReachablePositions(path, board, side).stream())
                .toList();
    }

    private List<Position> getReachablePositions(Path path, BoardReader board, Side side) {
        return path.findFirst(position -> !board.isEmpty(position))
                .filter(bridge -> board.getPiece(bridge).canBeBridge())
                .map(path::after)
                .map(afterBridge -> collectAfterBridge(afterBridge, board, side))
                .orElse(List.of());
    }

    private List<Position> collectAfterBridge(Path afterBridge, BoardReader board, Side side) {
        Path emptyPath = afterBridge.takeWhile(board::isEmpty);
        Optional<Position> target = afterBridge.findFirst(position -> !board.isEmpty(position));
        List<Position> reachable = new ArrayList<>(emptyPath.toList());
        target.filter(position -> isCatchable(position, board, side))
                .ifPresent(reachable::add);
        return reachable;
    }

    private boolean isCatchable(Position position, BoardReader board, Side side) {
        Piece piece = board.getPiece(position);
        return !piece.isAlly(side) && piece.canBeCapturedByJump();
    }
}
