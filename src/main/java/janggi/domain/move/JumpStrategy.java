package janggi.domain.move;

import janggi.domain.board.BoardReader;
import janggi.domain.piece.Piece;
import janggi.domain.space.Direction;
import janggi.domain.space.Path;
import janggi.domain.space.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class JumpStrategy implements MovementStrategy {
    private final List<Direction> defaultDirections;

    public JumpStrategy(List<Direction> defaultDirections) {
        this.defaultDirections = defaultDirections;
    }

    @Override
    public List<Path> generatePaths(Position current, BoardReader board) {
        Stream<Path> defaultPaths = defaultDirections.stream()
                .filter(current::canMove)
                .map(direction -> Path.ofContinuous(current, direction));

        Stream<Path> diagonalPaths = board.getPalaceDiagonals(current).stream()
                .filter(current::canMove)
                .map(direction -> Path.ofContinuous(current, direction)
                        .takeWhile(board::isInsidePalace));

        return Stream.concat(defaultPaths, diagonalPaths).toList();
    }

    @Override
    public List<Position> getMovablePositions(Position current, BoardReader board) {
        return generatePaths(current, board).stream()
                .flatMap(path -> getReachablePositions(path, board).stream())
                .toList();
    }

    private List<Position> getReachablePositions(Path path, BoardReader board) {
        return path.findFirst(position -> !board.isEmpty(position))
                .filter(bridge -> board.getPiece(bridge).canBeBridge())
                .map(path::after)
                .map(afterBridge -> collectAfterBridge(afterBridge, board))
                .orElse(List.of());
    }

    private List<Position> collectAfterBridge(Path afterBridge, BoardReader board) {
        Path emptyPath = afterBridge.takeWhile(board::isEmpty);
        Optional<Position> target = afterBridge.findFirst(position -> !board.isEmpty(position));
        List<Position> reachable = new ArrayList<>(emptyPath.toList());
        target.filter(position -> isCatchable(position, board))
                .ifPresent(reachable::add);
        return reachable;
    }

    private boolean isCatchable(Position position, BoardReader board) {
        Piece piece = board.getPiece(position);
        return piece.canBeCapturedByJump();
    }
}
