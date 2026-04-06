package domain.pieces;

import domain.board.Palace;
import domain.pieces.exception.InvalidMoveException;
import domain.pieces.exception.PieceErrorMessage;
import java.util.List;
import domain.movepolicy.destination.BasicDestinationRule;
import domain.movepolicy.destination.DestinationRule;
import domain.movepolicy.path.EmptyPathRule;
import domain.movepolicy.path.PathRule;
import domain.movement.Direction;
import domain.position.Position;
import domain.movement.SlidingDirectionFinder;
import domain.movement.SlidingPath;

public class Cha extends FullPiece {

    private static final SlidingDirectionFinder SLIDING_DIRECTION_FINDER = new SlidingDirectionFinder();
    private static final Palace palace = new Palace();

    public Cha(Side side) {
        super(side);
    }

    @Override
    protected void validateDestination(Position departure, Position destination) {
        if (departure.equals(destination)) {
            throw new InvalidMoveException(PieceErrorMessage.CHA_INVALID_MOVE);
        }
        if (!departure.isSameRow(destination)
                && !departure.isSameColumn(destination)
                && !palace.isChaAndPoDiagonalConnection(departure, destination)) {
            throw new InvalidMoveException(PieceErrorMessage.CHA_INVALID_MOVE);
        }
    }

    @Override
    protected List<Position> getPathPositions(Position departure, Position destination) {
        Direction direction = SLIDING_DIRECTION_FINDER.find(departure, destination);
        return new SlidingPath(direction).pathPositions(departure, destination);
    }

    @Override
    protected DestinationRule getDestinationRule() {
        return new BasicDestinationRule();
    }

    @Override
    protected PathRule getPathRule() {
        return new EmptyPathRule();
    }

    @Override
    public boolean isPo() {
        return false;
    }

    @Override
    public PieceType getType() {
        return PieceType.CHA;
    }
}
