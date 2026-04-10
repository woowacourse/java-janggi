package janggi.domain.piece;

import janggi.domain.Score;
import janggi.domain.Side;
import janggi.domain.board.BoardReader;
import janggi.domain.move.MovementStrategy;
import janggi.domain.space.Destinations;
import janggi.domain.space.Position;
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
                .filter(this::isWithinBoundary)
                .filter(destination -> board.isEmpty(destination) || !board.isAlly(destination, this.side))
                .toList();
        return new Destinations(validDestinations);
    }

    protected boolean isWithinBoundary(Position position) {
        return true;
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

    public Score getScore() {
        return getType().getScore();
    }
}
