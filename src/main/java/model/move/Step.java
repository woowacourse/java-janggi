package model.move;

import model.position.Position;

public class Step {
    private final Direction direction;
    private final boolean mustBeEmpty;

    Step(Direction direction, boolean mustBeEmpty) {
        this.direction = direction;
        this.mustBeEmpty = mustBeEmpty;
    }

    public Position move(Position position) {
        return position.move(direction);
    }

    public Direction direction() {
        return direction;
    }

    public boolean mustBeEmpty() {
        return mustBeEmpty;
    }
}
