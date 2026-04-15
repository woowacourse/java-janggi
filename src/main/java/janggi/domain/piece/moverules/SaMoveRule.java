package janggi.domain.piece.moverules;

import janggi.domain.board.Board;
import janggi.domain.board.Palace;
import janggi.domain.common.Direction;
import janggi.domain.common.Position;
import janggi.domain.common.Team;
import janggi.domain.piece.Piece;
import janggi.domain.route.Route;
import java.util.List;

public class SaMoveRule extends GeneralMoveRule {

    @Override
    public List<Route> findRoutes(Team team) {
        Route route1 = new Route(List.of(Direction.UP));
        Route route2 = new Route(List.of(Direction.RIGHT));
        Route route3 = new Route(List.of(Direction.DOWN));
        Route route4 = new Route(List.of(Direction.LEFT));
        return List.of(route1, route2, route3, route4);
    }

    @Override
    protected boolean canMove(Board board, Piece movePiece, List<Position> route, Position destination) {
        Palace palace = movePiece.selectPalace();
        return super.canMove(board, movePiece, route, destination) && palace.isInPalace(destination);
    }
}
