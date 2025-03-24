package janggi.piece;

import janggi.board.Position;
import java.util.Map;

public abstract class Piece {

    protected final Side side;

    public Piece(final Side side) {
        this.side = side;
    }

    public boolean isSameSide(final Piece other) {
        return this.side == other.side;
    }

    public boolean isSameSide(final Side side) {
        return this.side == side;
    }

    public Side getSide() {
        return side;
    }

    public abstract boolean canMove(final Position start, final Position end, Map<Position, Piece> board);

    public abstract boolean isCannon();
}
