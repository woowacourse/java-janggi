package janggi.domain.piece.strategy;

import janggi.domain.Path;
import janggi.domain.JanggiPosition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class HorseStrategy implements MoveStrategy {
    @Override
    public List<Path> findMovablePaths(JanggiPosition current) {
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

    private void addPath(List<Path> paths, JanggiPosition current,
                         int routeRow, int routeCol, int destRow, int destCol) {
        Optional<JanggiPosition> route = current.move(routeRow, routeCol);
        Optional<JanggiPosition> dest = current.move(destRow, destCol);
        if (route.isPresent() && dest.isPresent()) {
            paths.add(new Path(List.of(route.get()), dest.get()));
        }
    }
}
