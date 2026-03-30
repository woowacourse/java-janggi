package pieces;

import position.Delta;

public enum Side {

    CHO(
        new Delta(1, 0),
        new Delta(-1, 0),
        new Delta(0, -1),
        new Delta(0, 1)
    ),
    HAN(
        new Delta(-1, 0),
        new Delta(1, 0),
        new Delta(0, 1),
        new Delta(0, -1)
    );

    private final Delta forward;
    private final Delta back;
    private final Delta left;
    private final Delta right;

    Side(Delta forward, Delta back, Delta left, Delta right) {
        this.forward = forward;
        this.back = back;
        this.left = left;
        this.right = right;
    }

    public boolean isCho() {
        return this == CHO;
    }

    public Delta forwardDelta() {
        return forward;
    }

    public Delta backDelta() {
        return back;
    }

    public Delta leftDelta() {
        return left;
    }

    public Delta rightDelta() {
        return right;
    }

    public Delta leftForwardDelta() {
        return left.add(forward);
    }

    public Delta rightForwardDelta() {
        return right.add(forward);
    }

    public Delta rightBackDelta() {
        return right.add(back);
    }

    public Delta leftBackDelta() {
        return left.add(back);
    }
}
