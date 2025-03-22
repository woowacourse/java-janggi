package domain.piece.move;

import domain.Path;
import domain.position.Position;
import java.util.List;

public class FixedMoveRule implements MoveRule{

    private final List<Path> paths;

    public FixedMoveRule(List<Path> paths) {
        this.paths = paths;
    }

    @Override
    public List<Position> getIntermediatePath(Position from, Position to) {
        Path reachablePath = getReachablePath(from, to);
        return reachablePath.findPathPositionsFrom(from);
    }

    private Path getReachablePath(Position from, Position to) {
        return paths.stream()
                .filter(path -> path.canReachFromTo(from, to))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("지정한 포지션으로 이동할 수 없습니다."));
    }
}
