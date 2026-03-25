package domain.piece;

import domain.game.Side;
import java.util.Objects;

public abstract class Piece {

    protected final Side side;

    public Piece(Side side) {
        this.side = side;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Piece piece)) {
            return false;
        }
        return side == piece.side;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(side);
    }
}
