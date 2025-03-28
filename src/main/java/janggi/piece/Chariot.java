package janggi.piece;

import janggi.board.point.Point;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

public final class Chariot extends PalaceAffectedPiece {

    public Chariot(Camp camp) {
        super(camp);
    }

    @Override
    public Set<Point> findRoute(Point fromPoint, Point toPoint) {
        if (fromPoint.isHorizontal(toPoint)) {
            return findHorizontalRoute(fromPoint, toPoint);
        }
        if (fromPoint.isVertical(toPoint)) {
            return findVerticalRoute(fromPoint, toPoint);
        }
        return findDiagonalRoute(fromPoint, toPoint);
    }

    @Override
    protected void validateNonPalaceMove(Point fromPoint, Point toPoint) {
        validateLinearMove(fromPoint, toPoint);
    }

    @Override
    protected void validatePalaceMove(Point fromPoint, Point toPoint) {
        if (fromPoint.isDiagonal(toPoint)) {
            validateDiagonalPalaceMove(fromPoint, toPoint);
            return;
        }
        validateLinearMove(fromPoint, toPoint);
    }

    @Override
    protected void validateObstacleOnRoute(Set<Piece> piecesOnRoute) {
        if (!piecesOnRoute.isEmpty()) {
            throw new IllegalArgumentException("차는 기물을 넘어 이동할 수 없습니다.");
        }
    }

    private void validateDiagonalPalaceMove(Point fromPoint, Point toPoint) {
        if (!isDiagonalPalaceMove(fromPoint, toPoint)) {
            throw new IllegalArgumentException("차가 대각선으로 이동하려면, 허용된 지점에서만 가능합니다.");
        }
    }

    private void validateLinearMove(Point fromPoint, Point toPoint) {
        if (!fromPoint.isHorizontal(toPoint) && !fromPoint.isVertical(toPoint)) {
            throw new IllegalArgumentException("차는 수평 혹은 수직으로만 움직여야 합니다.");
        }
    }

    private Set<Point> findHorizontalRoute(Point fromPoint, Point toPoint) {
        return findLinearRoute(fromPoint.y(), fromPoint.x(), toPoint.x(), Point::new);
    }

    private Set<Point> findVerticalRoute(Point fromPoint, Point toPoint) {
        return findLinearRoute(fromPoint.x(), fromPoint.y(), toPoint.y(), (y, x) -> new Point(x, y));
    }

    private Set<Point> findLinearRoute(int fixed, int from, int to,
                                       BiFunction<Integer, Integer, Point> pointGenerator) {
        Set<Point> route = new HashSet<>();
        int start = Math.min(from, to) + 1;
        int end = Math.max(from, to);
        for (int i = start; i < end; i++) {
            route.add(pointGenerator.apply(i, fixed));
        }
        return route;
    }

    private Set<Point> findDiagonalRoute(Point fromPoint, Point toPoint) {
        Set<Point> route = new HashSet<>();
        Point current = fromPoint.getNextDiagonalStep(toPoint);
        while (!current.equals(toPoint)) {
            route.add(current);
            current = current.getNextDiagonalStep(toPoint);
        }
        return route;
    }

    @Override
    protected boolean canCapture(Piece otherPiece) {
        return isEnemy(otherPiece);
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.CHARIOT;
    }

    @Override
    public int getPoint() {
        return PieceSymbol.CHARIOT.getPoint();
    }
}
