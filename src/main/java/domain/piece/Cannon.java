package domain.piece;

import domain.Position;
import domain.Side;
import domain.board.BoardReader;
import domain.strategy.MovementStrategy;
import domain.strategy.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cannon extends Piece {
    public Cannon(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    protected List<Position> filterValidPositions(Position current, List<Path> paths, BoardReader board) {
        return paths.stream()
                .flatMap(path -> getReachablePositions(path, board).stream())
                .toList();
    }

    private List<Position> getReachablePositions(Path path, BoardReader board) {
        return path.findFirst(pos -> !board.isEmpty(pos))
                .filter(bridge -> !board.getPiece(bridge).isCannon())
                .map(path::after)
                .map(afterBridge -> collectAfterBridge(afterBridge, board))
                .orElse(List.of());
    }

    private List<Position> collectAfterBridge(Path afterBridge, BoardReader board) {
        Path emptyPath = afterBridge.takeWhile(board::isEmpty);
        Optional<Position> target = afterBridge.findFirst(pos -> !board.isEmpty(pos));
        List<Position> reachable = new ArrayList<>(emptyPath.toList());
        target.filter(t -> isCatchable(t, board))
                .ifPresent(reachable::add);
        return reachable;
    }

    private boolean isCatchable(Position targetPos, BoardReader board) {
        Piece piece = board.getPiece(targetPos);
        return !piece.isAlly(getSide()) && !piece.isCannon();
    }

    @Override
    public boolean isCannon() {
        return true;
    }

    @Override
    public String toString() {
        return "포";
    }
}
