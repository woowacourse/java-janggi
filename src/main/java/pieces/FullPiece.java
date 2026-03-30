package pieces;

import java.util.List;
import java.util.Objects;
import movepolicy.MoveContext;
import movepolicy.destination.DestinationRule;
import movepolicy.path.PathRule;
import position.Position;

public abstract class FullPiece implements Piece {

    private final Side side;

    public FullPiece(Side side) {
        this.side = side;
    }

    @Override
    public final boolean isEmpty() {
        return false;
    }

    @Override
    public final boolean isHan() {
        return side.isHan();
    }

    @Override
    public final boolean isCho() {
        return side.isCho();
    }

    @Override
    public final boolean isSameSide(Piece other) {
        if (other.isEmpty()) {
            return false;
        }
        if (isHan() && other.isHan()) {
            return true;
        }
        return isCho() && other.isCho();
    }

    @Override
    public final MoveContext askMoveContext(Position departure, Position destination) {
        validateDestination(departure, destination);
        List<Position> pathPositions = getPathPositions(departure, destination);
        DestinationRule destinationRule = getDestinationRule();
        PathRule pathRule = getPathRule();
        return new MoveContext(pathPositions, destinationRule, pathRule);
    }

    protected abstract void validateDestination(Position departure, Position destination);

    protected abstract List<Position> getPathPositions(Position departure, Position destination);

    protected abstract DestinationRule getDestinationRule();

    protected abstract PathRule getPathRule();

    public abstract PieceType getType();

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
        return Objects.hashCode(side);
    }
}
