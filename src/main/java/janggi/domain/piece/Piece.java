package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Side;

import java.util.Objects;
import java.util.Set;

public abstract class Piece {

    protected final Side side;

    public Piece(Side side) {
        this.side = side;
    }

    public abstract Set<Position> generateAvailableMovePositions(Board board, Position currentPosition);

    public boolean isSameSide(Side side) {
        return this.side == side;
    }

    public boolean isCannon() {
        return false;
    }

    public boolean isGeneral() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return side == piece.side;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(side);
    }
}
