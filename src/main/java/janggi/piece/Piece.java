package janggi.piece;

import janggi.board.Position;
import java.util.Map;

public abstract class Piece {

    protected final Side side;
    protected final int score;

    public Piece(final Side side, int score) {
        this.side = side;
        this.score = score;
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

    public int getScore() {
        return score;
    }

    public abstract boolean canMove(final Position start, final Position end, Map<Position, Piece> board);

    public abstract boolean isCannon();
}
