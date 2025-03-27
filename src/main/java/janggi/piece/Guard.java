package janggi.piece;

import janggi.board.Point;
import janggi.camp.Camp;
import java.util.Set;

public final class Guard extends Piece {

    public Guard(Camp camp) {
        super(camp);
    }

    @Override
    public void validateMove(Point from, Point to) {
    }

    @Override
    public void validateRouteObstacles(Set<Piece> piecesOnRoute) {

    }

    @Override
    public Set<Point> findRoute(Point from, Point to) {
        return Set.of();
    }

    @Override
    public PieceCategory getPieceCategory() {
        return PieceCategory.GUARD;
    }
}
