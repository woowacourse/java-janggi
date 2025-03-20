package janggi.piece;

import janggi.Camp;
import janggi.PieceSymbol;
import janggi.Point;
import janggi.board.Board;
import java.util.HashSet;
import java.util.Set;

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
            return findHorizontalRoute(fromPoint.getY(), fromPoint.getX(), toPoint.getX());
        }
        return findVerticalRoute(fromPoint.getX(), fromPoint.getY(), toPoint.getY());
    }

    private Set<Point> findHorizontalRoute(int fixedY, int fromX, int toX) {
        Set<Point> route = new HashSet<>();
        int start = Math.min(fromX, toX) + 1;
        int end = Math.max(fromX, toX);
        for (int i = start; i < end; i++) {
            route.add(new Point(i, fixedY));
        }
        return route;
    }

    private Set<Point> findVerticalRoute(int fixedX, int fromY, int toY) {
        Set<Point> route = new HashSet<>();
        int start = Math.min(fromY, toY) + 1;
        int end = Math.max(fromY, toY);
        for (int i = start; i < end; i++) {
            route.add(new Point(fixedX, i));
        }
        return route;
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.CHARIOT;
    }
}
