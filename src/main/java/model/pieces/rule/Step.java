package model.pieces.rule;

public class Step {
    private final Direction direction;
    private final boolean mustBeEmpty;

    Step(Direction direction, boolean mustBeEmpty) {
        this.direction = direction;
        this.mustBeEmpty = mustBeEmpty;
    }

    public Direction direction() {
        return direction;
    }

    public boolean mustBeEmpty() {
        return mustBeEmpty;
    }
}
