package janggi.piece;

import janggi.board.Position;
import janggi.board.Route;
import java.util.List;

public class Chariot extends Piece {

    public Chariot(final Side side) {
        super(side);
    }

    @Override
    public List<Route> computeCandidatePositions(final Position position) {
        return computeStraightRoutes(position, MOVE_LIMIT);
    }

    @Override
    public String getSymbol() {
        return "C";
    }

}
