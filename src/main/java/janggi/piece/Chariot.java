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
    public void validateMove(Point fromPoint, Point toPoint) {
        validateLinearMove(fromPoint, toPoint);
    }

    private void validateLinearMove(Point fromPoint, Point toPoint) {
        if (!fromPoint.isHorizontallyAlignedWith(toPoint) && !fromPoint.isVerticallyAlignedWith(toPoint)) {
            throw new IllegalArgumentException("차는 수평 혹은 수직으로만 움직여야 합니다.");
        }
    }

    @Override
    public void validatePathObstacles(Set<Piece> piecesOnRoute) {
        if (!piecesOnRoute.isEmpty()) {
            throw new IllegalArgumentException("차는 기물을 넘어 이동할 수 없습니다.");
        }
    }

    @Override
    public Set<Point> findRoute(Point fromPoint, Point toPoint) {
        if (fromPoint.isHorizontallyAlignedWith(toPoint)) {
            return fromPoint.findHorizontalPointsBetween(toPoint);
        }
        return fromPoint.findVerticalPointsBetween(toPoint);
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.CHARIOT;
    }
}
