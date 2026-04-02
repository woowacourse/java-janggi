package domain.piece;

import domain.Position;
import domain.Side;
import domain.board.BoardReader;
import domain.strategy.MovementStrategy;
import domain.strategy.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Chariot extends Piece {
    public Chariot(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    protected List<Position> filterValidPositions(Position current, List<Path> paths, BoardReader board) {
        return paths.stream()
                .flatMap(path -> getReachablePositions(path, board).stream())
                .toList();
    }

    private List<Position> getReachablePositions(Path path, BoardReader board) {
        Path emptyPath = path.takeWhile(board::isEmpty);
        Optional<Position> obstacle = path.findFirst(pos -> !board.isEmpty(pos));
        List<Position> reachable = new ArrayList<>(emptyPath.toList());
        obstacle.filter(position -> !board.getPiece(position).isAlly(getSide()))
                .ifPresent(reachable::add);
        return reachable;
    }

    @Override
    public String toString() {
        return "차";
    }
}
