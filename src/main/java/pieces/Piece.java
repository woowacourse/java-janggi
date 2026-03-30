package pieces;

import java.util.List;
import java.util.Objects;
import movepolicy.rule.MoveRule;
import position.Position;

public abstract class Piece {

    protected final Side side;

    public Piece(Side side) {
        this.side = side;
    }

    public final boolean isSameSide(Side side) {
        return this.side == side;
    }

    public final boolean isSameSide(Piece piece) {
        return isSameSide(piece.side);
    }

    public abstract void validateDestination(Position departure, Position destination);

    public abstract List<Position> findPathPositions(Position departure, Position destination);

    public abstract MoveRule getMoveRule();

    public abstract boolean isPo();

    public abstract PieceType type();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece other = (Piece) o;
        return side == other.side;
    }

    @Override
    public int hashCode() {
        return Objects.hash(side);
    }
}
