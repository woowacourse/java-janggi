package movepolicy.move;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import pieces.Side;
import position.Position;

public class Route {

    private final List<Step> steps;

    public Route(List<Step> steps) {
        this.steps = steps;
    }

    public Optional<Position> destinationOf(Position departure, Side side) {
        Position current = departure;
        for (Step step : steps) {
            try {
                current = step.move(current, side);
            } catch (IllegalArgumentException ignored) {
                return Optional.empty();
            }
        }
        return Optional.of(current);
    }

    public List<Position> getPathPositionsOf(Position departure, Side side) {
        List<Position> pathPositions = new ArrayList<>();
        Position current = departure;

        for (int i = 0; i < steps.size() - 1; i++) {
            current = steps.get(i).move(current, side);
            pathPositions.add(current);
        }
        return List.copyOf(pathPositions);
    }
}
