package movepolicy.move;

import java.util.ArrayList;
import java.util.List;
import pieces.Side;
import position.Position;

public class LinearRouteMovement implements Movement {

    @Override
    public boolean canReach(Position departure, Position destination, Side side) {
        return departure.isSameRow(destination) || departure.isSameColumn(destination);
    }

    @Override
    public List<Position> findPathPositions(Position departure, Position destination, Side side) {
        if (!canReach(departure, destination, side)) {
            throw new IllegalArgumentException("직선 이동이 아닙니다.");
        }

        Step step = decideDirection(departure, destination, side);
        List<Position> positions = new ArrayList<>();
        Position current = departure;
        while (!step.move(current, side).equals(destination)) {
            current = step.move(current, side);
            positions.add(current);
        }
        return List.copyOf(positions);
    }

    private Step decideDirection(Position departure, Position destination, Side side) {
        if (departure.isSameRow(destination)) {
            return decideRightOrLeft(departure, destination, side);
        }
        if (departure.isSameColumn(destination)) {
            return decideForwardOrBack(departure, destination, side);
        }
        throw new IllegalArgumentException("직선 이동이 아닙니다.");
    }

    private OneStep decideForwardOrBack(Position departure, Position destination, Side side) {
        if (destination.isBackRow(departure, side)) {
            return OneStep.BACK;
        }
        return OneStep.FORWARD;
    }

    private OneStep decideRightOrLeft(Position departure, Position destination, Side side) {
        if (destination.isLeftColumn(departure, side)) {
            return OneStep.LEFT;
        }
        return OneStep.RIGHT;
    }
}
