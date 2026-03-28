package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public enum GeneralMoveRule {

    UP(List.of(Position::up)),
    UP_CROSS_RIGHT(List.of(Position::upCrossRight)),
    UP_CROSS_LEFT(List.of(Position::upCrossLeft)),

    DOWN(List.of(Position::down)),
    DOWN_CROSS_RIGHT(List.of(Position::downCrossRight)),
    DOWN_CROSS_LEFT(List.of(Position::downCrossLeft)),

    LEFT(List.of(Position::left)),
    RIGHT(List.of(Position::right)),
    ;

    private final List<Function<Position, Position>> moveSteps;

    GeneralMoveRule(List<Function<Position, Position>> moveSteps) {
        this.moveSteps = moveSteps;
    }

    public Position destination(Position currentPosition) {
        Position position = currentPosition;

        for (Function<Position, Position> stepAction : moveSteps) {
            position = stepAction.apply(position);
        }

        return position;
    }

    public List<Position> route(Position currentPosition) {
        List<Position> nodes = new ArrayList<>();
        Position position = currentPosition;

        for (Function<Position, Position> stepAction : stepsToRoute()) {
            position = stepAction.apply(position);
            nodes.add(position);
        }

        return nodes;
    }

    private List<Function<Position, Position>> stepsToRoute() {
        return moveSteps.subList(0, moveSteps.size() - 1);
    }
}
