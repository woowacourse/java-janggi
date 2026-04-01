package domain.movement.direction;

import domain.movement.Vector;

public class Down implements Direction {

    @Override
    public Vector toForward() {
        return new Vector(1, 0);
    }

    @Override
    public Vector toBackword() {
        return new Vector(-1, 0);
    }

    @Override
    public Vector toLeft() {
        return new Vector(0, 1);
    }

    @Override
    public Vector toRight() {
        return new Vector(0, -1);
    }

    @Override
    public Vector toForwardLeft() {
        return new Vector(1, 1);
    }

    @Override
    public Vector toForwardRight() {
        return new Vector(1, -1);
    }

    @Override
    public Direction reverse() {
        return new Up();
    }
}
