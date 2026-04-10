package pieces;

import position.Delta;

public enum Side {

    CHO(
        Delta.up(),
        Delta.down(),
        Delta.right(),
        Delta.left()
    ),
    HAN(
        Delta.down(),
        Delta.up(),
        Delta.left(),
        Delta.right()
    ),
    ;

    private final Delta forward;
    private final Delta back;
    private final Delta right;
    private final Delta left;

    Side(Delta forward, Delta back, Delta right, Delta left) {
        this.forward = forward;
        this.back = back;
        this.right = right;
        this.left = left;
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

    public Side other() {
        if (this.isCho()) {
            return Side.HAN;
        }
        return Side.CHO;
    }
}
