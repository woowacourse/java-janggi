package janggi.domain;

import java.util.ArrayList;
import java.util.List;

public record Movements(List<Movement> movements) {
    public Route calculatePath(Position start) {
        List<Position> calculatedPath = new ArrayList<>(List.of(start));
        movements.forEach(movement -> calculatedPath.add(calculatedPath.getLast().move(movement)));
        return new Route(calculatedPath);
    }
}
