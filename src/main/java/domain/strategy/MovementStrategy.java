package domain.strategy;

import domain.Direction;
import domain.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class MovementStrategy {

    public List<Position> findRoute(List<List<Direction>> paths, Position sourcePosition) {
        List<Position> positions = new ArrayList<>();
        for(List<Direction> path: paths) {
            positions.addAll(buildRoute(path, sourcePosition));
        }
        return positions;
    }

    public List<Position> findPathTo(List<List<Direction>> paths, Position source, Position target) {
        for(List<Direction> path: paths) {
            List<Position> route = buildRoute(path, source);
            int targetIndex = route.indexOf(target);
            if(targetIndex != -1) {
                return new ArrayList<>(route.subList(0, targetIndex));
            }
        }
        return List.of();
    }

    protected abstract List<Position> buildRoute(List<Direction> route, Position sourcePosition);
}

