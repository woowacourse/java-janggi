package domain.pieces;

import domain.board.Palace;
import domain.movepolicy.destination.BasicDestinationRule;
import domain.movepolicy.destination.DestinationRule;
import domain.movepolicy.path.EmptyPathRule;
import domain.movepolicy.path.PathRule;
import domain.pieces.exception.InvalidMoveException;
import domain.pieces.exception.PieceErrorMessage;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class JolByeong extends FullPiece {

    private static final Palace PALACE = new Palace();

    public JolByeong(Side side) {
        super(side);
    }

    @Override
    protected void validateDestination(Position departure, Position destination) {
        List<Position> movableDestinations = movableDestinations(departure);

        if (movableDestinations.contains(destination)) {
            return;
        }
        if (canMovePalaceDiagonal(departure, destination)) {
            return;
        }
        throw new InvalidMoveException(PieceErrorMessage.JOL_BYEONG_INVALID_MOVE);
    }

    private boolean canMovePalaceDiagonal(Position departure, Position destination) {
        if (!PALACE.isSingleStepDiagonalConnection(departure, destination)) {
            return false;
        }
        if (this.getSide().isCho()) {
            return destination.row() > departure.row();
        }
        return destination.row() < departure.row();
    }

    private List<Position> movableDestinations(Position departure) {
        List<Position> destinations = new ArrayList<>();

        if (this.getSide().isCho()) {
            if (departure.canMoveUp()) {
                destinations.add(departure.moveUp());
            }
            if (departure.canMoveLeft()) {
                destinations.add(departure.moveLeft());
            }
            if (departure.canMoveRight()) {
                destinations.add(departure.moveRight());
            }
            return destinations;
        }

        if (departure.canMoveDown()) {
            destinations.add(departure.moveDown());
        }
        if (departure.canMoveLeft()) {
            destinations.add(departure.moveLeft());
        }
        if (departure.canMoveRight()) {
            destinations.add(departure.moveRight());
        }
        return destinations;
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
    public PieceType getType() {
        return PieceType.JOL_BYEONG;
    }
}
