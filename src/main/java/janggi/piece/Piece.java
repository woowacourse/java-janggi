package janggi.piece;

import janggi.Position;
import janggi.Side;
import java.util.List;

public abstract class Piece {

    protected final Side side;

    public Piece(final Side side) {
        this.side = side;
    }

    public boolean isSameSide(final Piece other) {
        return this.side == other.side;
    }

    public Side getSide() {
        return side;
    }

    public abstract List<Position> calculatePath(final Position start, final Position end);
}
