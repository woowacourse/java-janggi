package model.move;

import model.position.Position;

public class Step {
    private final Direction direction;

    Step(Direction direction) {
        this.direction = direction;
    }

    public Position move(Position position) {
        return position.move(direction);
    }
}
