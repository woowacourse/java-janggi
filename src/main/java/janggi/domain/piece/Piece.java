package janggi.domain.piece;

import janggi.domain.board.Position;
import janggi.domain.game.Score;
import java.util.Map;

public abstract class Piece {

    protected final Side side;
    protected final Score score;

    public Piece(final Side side, final Score score) {
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

    public Score getScore() {
        return score;
    }

    public abstract boolean canMove(final Position start, final Position end, Map<Position, Piece> board);

    public abstract boolean isCannon();
}
