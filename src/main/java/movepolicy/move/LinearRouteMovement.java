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
    public List<Position> getPathPositions(Position departure, Position destination, Side side) {
        if (!canReach(departure, destination, side)) {
            throw new IllegalArgumentException("직선 이동이 아닙니다.");
        }

        Step step = decideDirection(departure, destination, side);
        List<Position> pathPositions = new ArrayList<>();
        Position current = departure;
        while (!step.move(current, side).equals(destination)) {
            current = step.move(current, side);
            pathPositions.add(current);
        }
        return List.copyOf(pathPositions);
    }

    private Step decideDirection(Position departure, Position destination, Side side) {
        if (departure.isSameRow(destination)) {
            if (destination.isLeftColumn(departure, side)) {
                return OneStep.LEFT;
            }
            return OneStep.RIGHT;
        }
        if (departure.isSameColumn(destination)) {
            if (destination.isBackRow(departure, side)) {
                return OneStep.BACK;
            }
            return OneStep.FORWARD;
        }
        throw new IllegalArgumentException("직선 이동이 아닙니다.");
    }
}
