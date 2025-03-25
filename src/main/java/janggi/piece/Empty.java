package janggi.piece;

import janggi.board.JanggiBoard;
import janggi.board.Position;
import janggi.board.Route;
import java.util.List;

public class Empty extends Piece {

    public Empty() {
        super(Side.NONE);
    }

    @Override
    public List<Route> computeCandidatePositions(final Position position) {
        throw new IllegalArgumentException("[ERROR] 이 위치에는 말이 존재하지 않습니다.");
    }

    @Override
    public List<Position> filterReachableDestinations(final List<Route> candidateRoutes, final JanggiBoard board) {
        throw new IllegalArgumentException("[ERROR] 이 위치에는 말이 존재하지 않습니다.");
    }

    @Override
    public String getSymbol() {
        return "·";
    }

}
