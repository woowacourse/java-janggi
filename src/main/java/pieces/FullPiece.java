package pieces;

import java.util.List;
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

    public abstract boolean isPo();

    protected final boolean isHan() {
        return side.isHan();
    }

    protected final boolean isCho() {
        return side.isCho();
    }

    public final boolean isSameSide(FullPiece destinationPiece) {
        if (isHan() && destinationPiece.isHan()) {
            return true;
        }
        return isCho() && destinationPiece.isCho();
    }
}
