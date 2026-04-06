package domain.pieces;

import domain.board.Palace;
import domain.pieces.exception.InvalidMoveException;
import domain.pieces.exception.PieceErrorMessage;
import java.util.List;
import domain.movepolicy.destination.BasicDestinationRule;
import domain.movepolicy.destination.DestinationRule;
import domain.movepolicy.path.EmptyPathRule;
import domain.movepolicy.path.PathRule;
import domain.position.Position;

public class Sa extends FullPiece {
    private static final Palace PALACE = new Palace();

    public Sa(Side side) {
        super(side);
    }

    @Override
    protected void validateDestination(Position departure, Position destination) {
        if (!PALACE.isConnected(departure, destination)) {
            throw new InvalidMoveException(PieceErrorMessage.SA_INVALID_MOVE);
        }
    }

    @Override
    protected List<Position> getPathPositions(Position departure, Position destination) {
        return List.of();
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
        return PieceType.SA;
    }
}
