package janggi.piece;

import janggi.board.Position;
import janggi.board.Route;
import java.util.List;

public class Horse extends Piece {

    private static final int DIAGONAL_COUNT = 1;

    public Horse(final Side side) {
        super(side);
    }

    @Override
    public List<Route> computeCandidatePositions(final Position position) {
        return computeDiagonalRoutes(position, DIAGONAL_COUNT);
    }

    @Override
    public String getSymbol() {
        return "M";
    }

}
