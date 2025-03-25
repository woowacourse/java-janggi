package janggi.piece;

import janggi.board.Board;
import janggi.board.point.Point;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

public final class Chariot extends Piece {

    public Chariot(Camp camp, Board board) {
        super(camp, board);
    }

    @Override
    public void validateMove(Point fromPoint, Point toPoint) {
        validateLinearMove(fromPoint, toPoint);
        validateObstacleOnRoute(fromPoint, toPoint);
    }

    private void validateLinearMove(Point fromPoint, Point toPoint) {
        if (!fromPoint.isHorizontal(toPoint) && !fromPoint.isVertical(toPoint)) {
            throw new IllegalArgumentException("차는 수평 혹은 수직으로만 움직여야 합니다.");
        }
    }

    private void validateObstacleOnRoute(Point fromPoint, Point toPoint) {
        Set<Piece> pieces = getBoard().getPiecesByPoint(findRoute(fromPoint, toPoint));
        if (!pieces.isEmpty()) {
            throw new IllegalArgumentException("차는 기물을 넘어 이동할 수 없습니다.");
        }
    }

    private Set<Point> findRoute(Point fromPoint, Point toPoint) {
        boolean isHorizontal = fromPoint.isHorizontal(toPoint);
        if (isHorizontal) {
            return findRouteByFromAndTo(fromPoint.y(), fromPoint.x(), toPoint.x(), Point::new);
        }
        return findRouteByFromAndTo(fromPoint.x(), fromPoint.y(), toPoint.y(), (a, b) -> new Point(b, a));
    }

    private Set<Point> findRouteByFromAndTo(int fixed, int from, int to,
                                            BiFunction<Integer, Integer, Point> pointGenerator) {
        Set<Point> route = new HashSet<>();
        int start = Math.min(from, to) + 1;
        int end = Math.max(from, to);
        for (int i = start; i < end; i++) {
            route.add(pointGenerator.apply(i, fixed));
        }
        return route;
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.CHARIOT;
    }

    @Override
    protected boolean canCapture(Piece otherPiece) {
        return getCamp() != otherPiece.getCamp();
    }
}
