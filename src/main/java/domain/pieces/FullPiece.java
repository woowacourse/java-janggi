package domain.pieces;

import java.util.List;
import java.util.Objects;
import domain.movepolicy.MoveContext;
import domain.movepolicy.destination.DestinationRule;
import domain.movepolicy.path.PathRule;
import domain.position.Position;

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
    public final boolean isSameSide(Piece other) {
        if (other.isEmpty()) {
            return false;
        }
        return this.getSide() == other.getSide();
    }

    @Override
    public final MoveContext askMoveContext(Position departure, Position destination) {
        validateDestination(departure, destination);
        List<Position> pathPositions = getPathPositions(departure, destination);
        DestinationRule destinationRule = getDestinationRule();
        PathRule pathRule = getPathRule();
        return new MoveContext(pathPositions, destinationRule, pathRule);
    }

    @Override
    public final Side getSide() {
        return this.side;
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
