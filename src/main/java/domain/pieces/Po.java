package domain.pieces;

import domain.board.Palace;
import domain.pieces.exception.InvalidMoveException;
import domain.pieces.exception.PieceErrorMessage;
import java.util.List;
import domain.movepolicy.destination.DestinationRule;
import domain.movepolicy.destination.PoDestinationRule;
import domain.movepolicy.path.PathRule;
import domain.movepolicy.path.PoPathRule;
import domain.movement.Direction;
import domain.position.Position;
import domain.movement.SlidingDirectionFinder;
import domain.movement.SlidingPath;

public class Po extends FullPiece {

    private static final SlidingDirectionFinder SLIDING_DIRECTION_FINDER = new SlidingDirectionFinder();
    private static final Palace PALACE = new Palace();

    public Po(Side side) {
        super(side);
    }

    @Override
    protected void validateDestination(Position departure, Position destination) {
        if (departure.equals(destination)) {
            throw new InvalidMoveException(PieceErrorMessage.PO_INVALID_MOVE);
        }
        if (!departure.isSameRow(destination) && !departure.isSameColumn(destination)
                && !PALACE.isChaAndPoDiagonalConnection(departure, destination)) {
            throw new InvalidMoveException(PieceErrorMessage.PO_INVALID_MOVE);
        }
        if (!departure.isGapBiggerThanOne(destination)) {
            throw new InvalidMoveException(PieceErrorMessage.PO_ONE_SPACE_MOVE);
        }
    }

    @Override
    protected List<Position> getPathPositions(Position departure, Position destination) {
        Direction direction = SLIDING_DIRECTION_FINDER.find(departure, destination);
        return new SlidingPath(direction).pathPositions(departure, destination);
    }

    @Override
    protected DestinationRule getDestinationRule() {
        return new PoDestinationRule();
    }

    @Override
    protected PathRule getPathRule() {
        return new PoPathRule();
    }

    @Override
    public boolean isPo() {
        return true;
    }

    @Override
    public PieceType getType() {
        return PieceType.PO;
    }
}
