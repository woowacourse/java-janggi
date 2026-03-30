package janggi.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record Movements(List<Movement> movements) {
    public Optional<Route> calculatePath(Position start) {
        List<Position> calculatedPath = new ArrayList<>(List.of(start));
        try {
            movements.forEach(movement -> calculatedPath.add(calculatedPath.getLast().move(movement)));
            return Optional.of(new Route(calculatedPath));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
