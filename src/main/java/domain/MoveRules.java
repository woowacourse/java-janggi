package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public interface MoveRules {

    List<UnaryOperator<Position>> moveSteps();

    default Position destination(Position currentPosition) {
        Position position = currentPosition;

        for (UnaryOperator<Position> stepAction : moveSteps()) {
            position = stepAction.apply(position);
        }

        return position;
    }

    default List<Position> route(Position currentPosition) {
        List<Position> nodes = new ArrayList<>();
        Position position = currentPosition;

        for (UnaryOperator<Position> stepAction : stepsToRoute()) {
            position = stepAction.apply(position);
            nodes.add(position);
        }

        return nodes;
    }

    private List<UnaryOperator<Position>> stepsToRoute() {
        List<UnaryOperator<Position>> moveSteps = moveSteps();
        return moveSteps.subList(0, moveSteps.size() - 1);
    }
}
