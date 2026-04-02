package domain.piece.strategy;

import domain.board.Position;

import java.util.ArrayList;
import java.util.List;

public class BlockedPath {
    private final List<Delta> blockedPath;

    public BlockedPath(List<Delta> blockedPath) {
        this.blockedPath = blockedPath;
    }

    public List<Position> findPath(Position from) {
        List<Position> path = new ArrayList<>();
        for (Delta delta : blockedPath) {
            path.add(from.move(delta));
        }

        return path;
    }
}
