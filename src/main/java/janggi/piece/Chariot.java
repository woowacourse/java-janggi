package janggi.piece;

import janggi.board.JanggiBoard;
import janggi.board.Position;
import janggi.board.Route;
import java.util.ArrayList;
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
    public List<Position> filterReachableDestinations(final List<Route> candidateRoutes, final JanggiBoard board) {
        List<Position> reachablePositions = new ArrayList<>();
        for (Route route : candidateRoutes) {
            List<Position> positions = route.getPositions();
            for (Position position : positions) {
                if (board.isOutOfRange(position) || isAllyWith(board.findPieceBy(position))) {
                    break;
                }
                if (board.isPositionHasPiece(position) && isEnemyWith(board.findPieceBy(position))) {
                    reachablePositions.add(position);
                    break;
                }
                reachablePositions.add(position);
            }
        }
        return reachablePositions;
    }

    @Override
    public String getSymbol() {
        return "C";
    }

}
