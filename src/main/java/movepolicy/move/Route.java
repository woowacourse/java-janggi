package movepolicy.move;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import pieces.Side;
import position.Position;

public class Route {

    private final List<Step> steps;

    public Route(final List<Step> steps) {
        this.steps = steps;
    }

    public Optional<Position> destinationOf(final Position departure, final Side side) {
        Position current = departure;
        for (Step step : steps) {
            if (!step.canMove(current, side)) {
                return Optional.empty();
            }
            current = step.move(current, side);
        }
        return Optional.of(current);
    }

    public List<Position> findPathPositions(final Position departure, final Side side) {
        final List<Position> positions = new ArrayList<>();
        Position current = departure;
        for (Step step : steps) {
            current = step.move(current, side);
            positions.add(current);
        }

        removeDestination(positions);
        return List.copyOf(positions);
    }

    private void removeDestination(final List<Position> positions) {
        positions.removeLast();
    }
}
