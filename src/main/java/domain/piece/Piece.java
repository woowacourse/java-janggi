package domain.piece;

import domain.Position;
import domain.Side;
import domain.strategy.MovementStrategy;

import java.util.List;

import static constant.ErrorMessage.CANNOT_CAPTURE_OWN_PIECE;
import static constant.ErrorMessage.ROUTE_BLOCKED;

public abstract class Piece {

    protected final Side side;
    protected final MovementStrategy movementStrategy;

    protected Piece(Side side, MovementStrategy movementStrategy) {
        this.side = side;
        this.movementStrategy = movementStrategy;
    }

    public boolean isSameSide(Side side) {
        return this.side == side;
    }

    public Side getSide() {
        return side;
    }

    public boolean isValidRoute(List<Piece> piecesOnPath) {
        for (Piece piece : piecesOnPath) {
            if (!piece.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean isValidTarget(Piece targetPiece) {
        return !targetPiece.isSameSide(this.side);
    }

    public void checkRoute(List<Piece> piecesOnPath) {
        if (!isValidRoute(piecesOnPath)) {
            throw new IllegalArgumentException(ROUTE_BLOCKED);
        }
    }

    public void checkTarget(Piece targetPiece) {
        if (side.equals(targetPiece.side)) {
            throw new IllegalArgumentException(CANNOT_CAPTURE_OWN_PIECE);
        }
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean isCannon() {
        return false;
    }

    public boolean isKing() {
        return false;
    }

    public abstract List<Position> findRoute(Position source);

    public abstract List<Position> findPathTo(Position source, Position target);

    public abstract String getName();

    public abstract double getScore();
}
