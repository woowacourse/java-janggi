package position;

import movement.Movement;

public enum LineDirection {

    UP(1, 1),
    DOWN(10, -1),
    ;

    private final int baseIdx;
    private final int delta;

    LineDirection(int baseIdx, int delta) {
        this.baseIdx = baseIdx;
        this.delta = delta;
    }

    public int getLineFarBy(int value) {
        return baseIdx + delta * value;
    }

    public LineDirection opposite() {
        if (this == LineDirection.DOWN) {
            return UP;
        }
        return DOWN;
    }

    public Movement getForward() {
        return Movement.findVerticalByY(delta);
    }
}
