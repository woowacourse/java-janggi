package janggi.domain.piece;

public record Vector(int y, int x) {

    public Vector side(Side side) {
        if (side == Side.CHO) {
            return new Vector(-y, x);

        }
        return new Vector(y, x);
    }
}
