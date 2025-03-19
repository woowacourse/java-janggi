package janggi.domain;

import janggi.domain.piece.PieceBehavior;
import janggi.domain.piece.Side;
import java.util.List;
import java.util.Objects;

public class Piece {

    private final Side side;
    private final PieceBehavior pieceBehavior;

    public Piece(Side side, PieceBehavior pieceBehavior) {
        this.side = side;
        this.pieceBehavior = pieceBehavior;
    }

    public List<Position> availableMovePositions(Position currentPosition) {
        return pieceBehavior.generateMovePosition(this.side, currentPosition);
    }

    public String toName() {
        if (side == Side.CHO) {
            return "\u001B[32m" + pieceBehavior.toName() + "\u001B[0m";
        }
        return "\u001B[31m" + pieceBehavior.toName() + "\u001B[0m";
    }

    public boolean isSameSide(Side side) {
        return this.side == side;
    }

    @Override
    public int hashCode() {
        return Objects.hash(side, pieceBehavior);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return side == piece.side && Objects.equals(pieceBehavior, piece.pieceBehavior);
    }
}
