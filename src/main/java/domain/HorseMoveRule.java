package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public enum HorseMoveRule {
    UP_CROSS_RIGHT(List.of(Position::up, Position::upCrossRight)),
    UP_CROSS_LEFT(List.of(Position::up, Position::upCrossLeft)),

    DOWN_CROSS_RIGHT(List.of(Position::down, Position::downCrossRight)),
    DOWN_CROSS_LEFT(List.of(Position::down, Position::downCrossLeft)),

    RIGHT_CROSS_UP(List.of(Position::right, Position::upCrossRight)),
    RIGHT_CROSS_DOWN(List.of(Position::right, Position::downCrossRight)),

    LEFT_CROSS_UP(List.of(Position::left, Position::upCrossLeft)),
    LEFT_CROSS_DOWN(List.of(Position::left, Position::downCrossLeft)),
    ;

    private final List<Function<Position, Position>> moveSteps;

    HorseMoveRule(List<Function<Position, Position>> moveSteps) {
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
