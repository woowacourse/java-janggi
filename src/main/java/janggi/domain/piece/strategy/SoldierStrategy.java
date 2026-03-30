package janggi.domain.piece.strategy;

import janggi.domain.Path;
import janggi.domain.position.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SoldierStrategy implements MoveStrategy {
    private final int direction;

    public SoldierStrategy(int direction) {
        this.direction = direction;
    }

    @Override
    public List<Path> findMovablePaths(Position current) {
        List<Path> paths = new ArrayList<>();

        addPath(paths, current, direction, 0);
        addPath(paths, current, 0, 1);
        addPath(paths, current, 0, -1);

        return Collections.unmodifiableList(paths);
    }

    private void addPath(List<Path> paths, Position current, int destRow, int destCol) {
        current.move(destRow, destCol)
                .map(dest -> new Path(List.of(), dest))
                .ifPresent(paths::add);
    }
}
