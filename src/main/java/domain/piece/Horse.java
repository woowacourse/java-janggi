package domain.piece;

import domain.game.Side;

public class Horse extends Piece {

    public Horse(Side side) {
        super(side);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Horse)) {
            return false;
        }

        return super.equals(other);
    }

    @Override
    public String toString() {
        return "Horse{" +
                "side=" + side +
                '}';
    }
}
