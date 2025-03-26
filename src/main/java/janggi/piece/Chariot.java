package janggi.piece;

import janggi.board.Point;
import janggi.camp.Camp;
import janggi.view.PieceSymbol;
import java.util.Set;

public final class Chariot extends Piece {

    public Chariot(Camp camp) {
        super(camp);
    }

    @Override
    public void validateMove(Point from, Point to) {
        validateLinearMove(from, to);
    }

    private void validateLinearMove(Point from, Point to) {
        if (!from.isHorizontallyAlignedWith(to) && !from.isVerticallyAlignedWith(to)) {
            throw new IllegalArgumentException("차는 수평 혹은 수직으로만 움직여야 합니다.");
        }
    }

    @Override
    public void validateRouteObstacles(Set<Piece> piecesOnRoute) {
        if (!piecesOnRoute.isEmpty()) {
            throw new IllegalArgumentException("차는 기물을 넘어 이동할 수 없습니다.");
        }
    }

    @Override
    public Set<Point> findRoute(Point from, Point to) {
        if (from.isHorizontallyAlignedWith(to)) {
            return from.findHorizontalPointsBetween(to);
        }
        return from.findVerticalPointsBetween(to);
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.CHARIOT;
    }
}
