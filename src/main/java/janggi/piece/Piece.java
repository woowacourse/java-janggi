package janggi.piece;

import janggi.board.Position;
import janggi.board.Route;
import java.util.List;

public abstract class Piece {

    protected static final int MOVE_LIMIT = 10;

    private final Side side;

    protected Piece(final Side side) {
        this.side = side;
    }

    public abstract List<Route> computeCandidatePositions(final Position position);

    public abstract String getSymbol();

    public boolean isCho() {
        return side == Side.CHO;
    }

    public boolean isHan() {
        return side == Side.HAN;
    }

}
