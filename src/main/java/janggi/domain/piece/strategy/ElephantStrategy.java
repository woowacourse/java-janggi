package janggi.domain.piece.strategy;

import janggi.domain.Path;
import janggi.domain.JanggiPosition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ElephantStrategy implements MoveStrategy {
    @Override
    public List<Path> findMovablePaths(JanggiPosition current) {
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

    private void addPath(List<Path> paths, JanggiPosition current,
                         int routeRow1, int routeCol1,
                         int routeRow2, int routeCol2,
                         int destRow, int destCol) {
        Optional<JanggiPosition> route1 = current.move(routeRow1, routeCol1);
        Optional<JanggiPosition> route2 = current.move(routeRow2, routeCol2);
        Optional<JanggiPosition> dest = current.move(destRow, destCol);

        if (route1.isPresent() && route2.isPresent() && dest.isPresent()) {
            paths.add(new Path(List.of(route1.get(), route2.get()), dest.get()));
        }
    }
}
