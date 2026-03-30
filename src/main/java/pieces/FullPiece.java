package pieces;

import java.util.List;
import java.util.Objects;
import movepolicy.rule.MoveRule;
import position.Position;

public abstract class FullPiece implements Piece {

    protected final Side side;

    public FullPiece(Side side) {
        this.side = side;
    }

    @Override
    public final boolean isEmpty() {
        return false;
    }

    @Override
    public final FullPiece asFullPiece() {
        return this;
    }

    public final boolean isSameSide(Side side) {
        return this.side == side;
    }

    public final boolean isSameSide(FullPiece piece) {
        return isSameSide(piece.side);
    }

    public abstract void validateDestination(Position departure, Position destination);

    public abstract List<Position> getInterveningPositions(Position departure, Position destination);

    public abstract MoveRule getMoveRule();

    public abstract boolean isPo();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FullPiece other = (FullPiece) o;
        return side == other.side;
    }

    @Override
    public int hashCode() {
        return Objects.hash(side);
    }
}
