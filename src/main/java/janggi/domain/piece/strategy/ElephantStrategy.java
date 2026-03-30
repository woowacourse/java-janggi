package janggi.domain.piece.strategy;

import janggi.domain.Path;
import janggi.domain.WayPoints;
import janggi.domain.position.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ElephantStrategy implements MoveStrategy {
    @Override
    public List<Path> findMovablePaths(Position current) {
        List<Path> paths = new ArrayList<>();
        addPath(paths, current, 1, 0, 2, 1, 3, 2);
        addPath(paths, current, 0, 1, 1, 2, 2, 3);
        addPath(paths, current, 0, 1, -1, 2, -2, 3);
        addPath(paths, current, -1, 0, -2, 1, -3, 2);
        addPath(paths, current, 1, 0, 2, -1, 3, -2);
        addPath(paths, current, 0, -1, 1, -2, 2, -3);
        addPath(paths, current, 0, -1, -1, -2, -2, -3);
        addPath(paths, current, -1, 0, -2, -1, -3, -2);
        return Collections.unmodifiableList(paths);
    }

    private void addPath(List<Path> paths, Position current,
                         int routeRow1, int routeCol1,
                         int routeRow2, int routeCol2,
                         int destRow, int destCol) {
        current.move(routeRow1, routeCol1)
                .flatMap(route1 -> current.move(routeRow2, routeCol2)
                        .flatMap(route2 -> current.move(destRow, destCol)
                                .map(dest -> Path.of(List.of(route1, route2), dest))))
                .ifPresent(paths::add);
    }
}
