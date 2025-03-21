package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Side;
import janggi.domain.move.Position;
import janggi.domain.piece.behavior.General;
import java.util.Objects;
import java.util.Set;

public class Piece {

    private final Side side;
    private final PieceBehavior pieceBehavior;

    public Piece(Side side, PieceBehavior pieceBehavior) {
        this.side = side;
        this.pieceBehavior = pieceBehavior;
    }

    public boolean isSameSide(Side side) {
        return this.side == side;
    }

    public boolean isGeneral(Side side) {
        return this.side == side && pieceBehavior instanceof General;
    }

    public Set<Position> getAvailableMovePositions(Board board, Position currentPosition) {
        return pieceBehavior.generateAvailableMovePositions(board, this.side, currentPosition);
    }

    public String toName() {
        if (side == Side.CHO) {
            return "\u001B[32m" + pieceBehavior.toName() + "\u001B[0m";
        }
        return "\u001B[31m" + pieceBehavior.toName() + "\u001B[0m";
    }

    public PieceBehavior getPieceBehavior() {
        return pieceBehavior;
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
