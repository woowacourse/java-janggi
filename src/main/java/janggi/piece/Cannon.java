package janggi.piece;

import janggi.board.JanggiBoard;
import janggi.board.Position;
import janggi.board.Route;
import java.util.ArrayList;
import java.util.List;

public class Cannon extends Piece {

    public Cannon(final Side side) {
        super(Symbol.CANNON, side);
    }

    @Override
    public List<Position> filterReachableDestinations(final Position selectedPosition, final JanggiBoard board) {
        List<Route> candidateRoutes = computeStraightRoutes(selectedPosition, MOVE_LIMIT);
        List<Position> reachablePositions = new ArrayList<>();
        for (Route route : candidateRoutes) {
            List<Position> positions = route.getPositions();
            boolean hasJumped = false;
            for (Position position : positions) {
                if (board.isOutOfRange(position) || board.isPositionCannon(position)) {
                    break;
                }
                if (!hasJumped && board.isPositionHasPiece(position)) {
                    hasJumped = true;
                    continue;
                }

                if (hasJumped) {
                    if (board.isPositionHasPiece(position)) {
                        if (isEnemyWith(board.findPieceBy(position))) {
                            reachablePositions.add(position);
                        }
                        break;
                    }
                    reachablePositions.add(position);
                }
            }
        }
        return reachablePositions;
    }

}
