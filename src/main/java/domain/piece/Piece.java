package domain.piece;

import domain.Destinations;
import domain.Position;
import domain.Side;
import domain.board.BoardReader;
import domain.strategy.MovementStrategy;
import java.util.List;

public abstract class Piece {
    private final Side side;
    private final MovementStrategy movementStrategy;

    protected Piece(Side side, MovementStrategy movementStrategy) {
        this.side = side;
        this.movementStrategy = movementStrategy;
    }

    public abstract PieceType getType();

    public Destinations findDestinations(Position current, BoardReader board) {
        List<Position> destinations = movementStrategy.getMovablePositions(current, board);
        List<Position> validDestinations = destinations.stream()
                .filter(destination -> board.isEmpty(destination) || !board.isAlly(destination, this.side))
                .toList();
        return new Destinations(validDestinations);
    }

    public boolean isAlly(Side other) {
        return this.side.isAlly(other);
    }

    public boolean isVital() {
        return false;
    }

    public boolean canBeBridge() {
        return true;
    }

    public boolean canBeCapturedByJump() {
        return true;
    }

    public Side getSide() {
        return side;
    }
}
