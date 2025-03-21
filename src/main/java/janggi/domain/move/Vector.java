package janggi.domain.move;

import janggi.domain.Side;

public record Vector(int y, int x) {

    public static Vector rotate(Vector vector) {
        return new Vector(vector.x(), -vector.y());
    }

    public Vector side(Side side) {
        if (side == Side.CHO) {
            return new Vector(-y, x);
        }
        return new Vector(y, x);
    }
}
