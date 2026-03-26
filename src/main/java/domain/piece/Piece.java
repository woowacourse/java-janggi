package domain.piece;

import domain.game.Side;
import java.util.Objects;

public abstract class Piece {

    protected final Side side;

    public Piece(Side side) {
        this.side = side;
    }

    public boolean hasDifferentSide(Side side) {
        return this.side != side;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Piece piece)) {
            return false;
        }
        if (side != piece.side) {
            return false;
        }

        return this.getClass().equals(piece.getClass());
    }

    @Override
    public int hashCode() {
        return Objects.hash(side, this.getClass());
    }

    @Override
    public final String toString() {
        return getClass().getSimpleName() + "{" +
                "side=" + side +
                '}';
    }
}
