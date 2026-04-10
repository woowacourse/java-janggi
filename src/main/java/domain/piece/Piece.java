package domain.piece;

import domain.board.Intersection;
import domain.game.Side;
import java.util.List;
import java.util.Objects;

public abstract class Piece {

    protected final Side side;

    public Piece(Side side) {
        this.side = side;
    }

    public final Side getSide() {
        return side;
    }

    public final boolean hasSameSide(Side side) {
        return this.side == side;
    }

    public final boolean hasDifferentSide(Side side) {
        return !hasSameSide(side);
    }

    public abstract List<Intersection> movableIntersections(
            Intersection from,
            AlivePieces alivePieces
    );

    public abstract double getScore();

    public abstract boolean canBelongToWing();

    public abstract boolean isRoyalPiece();

    protected abstract boolean isScreenable();

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
