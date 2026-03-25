package domain.piece;

import domain.Side;
import java.util.Objects;

public abstract class Piece {

    private final Side side;

    public Piece(Side side) {
        this.side = side;
    }

    public boolean isChu() {
        return side.isChu();
    }

    public boolean isHan() {
        return side.isHan();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return side == piece.side;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(side);
    }
}
