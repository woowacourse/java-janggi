package pieces;

import movepolicy.destination.DestinationRule;
import movepolicy.MoveContext;
import movepolicy.path.PathRule;
import java.util.List;
import position.Position;

public abstract class PieceImpl implements Piece {
    private final Side side;

    public PieceImpl(Side side) {
        this.side = side;
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
    public final boolean isSameSide(Piece destinationPiece) {
        if (isHan() && destinationPiece.isHan()) {
            return true;
        }
        return isCho() && destinationPiece.isCho();
    }

    @Override
    public final MoveContext askMoveContext(Position departure, Position destination) {
        validateDestination(departure, destination);
        List<Position> pathPositions = getPathPositions(departure, destination);
        DestinationRule destinationRule = getDestinationRule();
        PathRule pathRule = getPathRule();
        return new MoveContext(pathPositions, destinationRule, pathRule);
    }

    abstract void validateDestination(Position departure, Position destination);

    abstract List<Position> getPathPositions(Position departure, Position destination);

    abstract DestinationRule getDestinationRule();

    abstract PathRule getPathRule();
}
