package janggi.domain.piece.strategy;

import janggi.domain.Path;
import janggi.domain.WayPoints;
import janggi.domain.position.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HorseStrategy implements MoveStrategy {
    @Override
    public List<Path> findMovablePaths(Position current) {
        List<Path> paths = new ArrayList<>();

        addPath(paths, current, 1, 0, 2, 1);
        addPath(paths, current, 0, 1, 1, 2);
        addPath(paths, current, 0, 1, -1, 2);
        addPath(paths, current, -1, 0, -2, 1);
        addPath(paths, current, 1, 0, 2, -1);
        addPath(paths, current, 0, -1, 1, -2);
        addPath(paths, current, 0, -1, -1, -2);
        addPath(paths, current, -1, 0, -2, -1);

        return Collections.unmodifiableList(paths);
    }

    private void addPath(List<Path> paths, Position current,
                         int routeRow, int routeCol, int destRow, int destCol) {
        current.move(routeRow, routeCol)
                .flatMap(route -> current.move(destRow, destCol)
                        .map(dest -> Path.of(List.of(route), dest)))
                .ifPresent(paths::add);
    }
}
