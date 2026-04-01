package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public interface MoveRules {

    List<Function<Position, Position>> moveSteps();

    default Position destination(Position currentPosition) {
        Position position = currentPosition;

        for (Function<Position, Position> stepAction : moveSteps()) {
            position = stepAction.apply(position);
        }

        return position;
    }

    default List<Position> route(Position currentPosition) {
        List<Position> nodes = new ArrayList<>();
        Position position = currentPosition;

        for (Function<Position, Position> stepAction : stepsToRoute()) {
            position = stepAction.apply(position);
            nodes.add(position);
        }

        return nodes;
    }

    private List<Function<Position, Position>> stepsToRoute() {
        List<Function<Position, Position>> moveSteps = moveSteps();
        return moveSteps.subList(0, moveSteps.size() - 1);
    }
}
