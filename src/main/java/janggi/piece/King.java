package janggi.piece;

import janggi.board.Position;
import janggi.board.Route;
import java.util.List;

public class King extends Piece {

    private static final int ALLOWED_MOVE = 1;

    public King(final Side side) {
        super(side);
    }

    @Override
    public List<Route> computeCandidatePositions(final Position position) {
        return computeStraightRoutes(position, ALLOWED_MOVE);
    }

    @Override
    public String getSymbol() {
        return "G";
    }

}
