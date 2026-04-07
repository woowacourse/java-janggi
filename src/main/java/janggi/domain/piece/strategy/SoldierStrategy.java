package janggi.domain.piece.strategy;

import janggi.domain.Path;
import janggi.domain.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SoldierStrategy implements MoveStrategy {
    private final Direction forwardDirection;

    public SoldierStrategy(Direction forwardDirection) {
        this.forwardDirection = forwardDirection;
    }

    @Override
    public List<Path> findMovablePaths(Position current) {
        List<Path> paths = new ArrayList<>();

        addPath(paths, current, forwardDirection);
        addPath(paths, current, Direction.west());
        addPath(paths, current, Direction.east());

        return Collections.unmodifiableList(paths);
    }

    private void addPath(List<Path> paths, Position current, Direction direction) {
        direction.findNextPosition(current)
                .map(position -> new Path(List.of(), position))
                .ifPresent(paths::add);
    }
}
