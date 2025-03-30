package janggi.domain.piece;

import janggi.domain.Side;
import janggi.domain.movement.Position;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class Piece {

    protected final Side side;

    public Piece(Side side) {
        this.side = side;
    }

    public boolean canMove(Map<Position, Piece> pieces, Position selectedPosition, Position targetPosition) {
        Set<Position> availableMoves = generateAvailableMovePositions(pieces, selectedPosition);
        return availableMoves.contains(targetPosition);
    }

    protected abstract Set<Position> generateAvailableMovePositions(Map<Position, Piece> pieces, Position currentPosition);

    public boolean isSameSide(Piece other) {
        return isSameSide(other.side);
    }

    public boolean isSameSide(Side side) {
        return this.side == side;
    }

    public boolean isCannon() {
        return false;
    }

    public boolean isGeneral() {
        return false;
    }

    public double getPoints() {
        return 0;
    }

    public Side getSide() {
        return side;
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
