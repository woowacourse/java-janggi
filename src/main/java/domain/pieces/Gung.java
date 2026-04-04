package domain.pieces;

import domain.pieces.exception.InvalidMoveException;
import domain.pieces.exception.PieceErrorMessage;
import java.util.List;
import domain.movepolicy.destination.BasicDestinationRule;
import domain.movepolicy.destination.DestinationRule;
import domain.movepolicy.path.EmptyPathRule;
import domain.movepolicy.path.PathRule;
import domain.position.Position;

public class Gung extends FullPiece {

    public Gung(Side side) {
        super(side);
    }

    @Override
    protected void validateDestination(Position departure, Position destination) {
        java.util.ArrayList<Position> movableDestinations = new java.util.ArrayList<>();
        if (departure.canMoveUp()) {
            movableDestinations.add(departure.moveUp());
        }
        if (departure.canMoveDown()) {
            movableDestinations.add(departure.moveDown());
        }
        if (departure.canMoveLeft()) {
            movableDestinations.add(departure.moveLeft());
        }
        if (departure.canMoveRight()) {
            movableDestinations.add(departure.moveRight());
        }
        if (!movableDestinations.contains(destination)) {
            throw new InvalidMoveException(PieceErrorMessage.GUNG_INVALID_MOVE);
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
        return PieceType.GUNG;
    }
}
