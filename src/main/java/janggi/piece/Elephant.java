package janggi.piece;

import janggi.board.Position;
import janggi.board.Route;
import java.util.List;

public class Elephant extends Piece {

    private static final int DIAGONAL_COUNT = 2;

    public Elephant(final Side side) {
        super(side);
    }

    @Override
    public List<Route> computeCandidatePositions(final Position position) {
        return computeDiagonalRoutes(position, DIAGONAL_COUNT);
    }

    @Override
    public String getSymbol() {
        return "E";
    }

}
