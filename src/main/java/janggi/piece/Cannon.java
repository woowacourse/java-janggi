package janggi.piece;

import janggi.board.point.Point;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

public final class Cannon extends PalaceAffectedPiece {

    private static final int POSSIBLE_JUMP_OVER_PIECE_COUNT = 1;

    public Cannon(Camp camp) {
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
        validatePieceCount(piecesOnRoute);
        validateNotJumpOverCannon(piecesOnRoute);
    }

    private void validateDiagonalPalaceMove(Point fromPoint, Point toPoint) {
        if (!isDiagonalPalaceMove(fromPoint, toPoint)) {
            throw new IllegalArgumentException("포가 대각선으로 이동하려면, 허용된 지점에서만 가능합니다.");
        }
    }

    private void validateLinearMove(Point fromPoint, Point toPoint) {
        if (!fromPoint.isHorizontal(toPoint) && !fromPoint.isVertical(toPoint)) {
            throw new IllegalArgumentException("포는 수평 혹은 수직으로만 움직여야 합니다.");
        }
    }

    private void validatePieceCount(Set<Piece> pieces) {
        if (pieces.size() != POSSIBLE_JUMP_OVER_PIECE_COUNT) {
            throw new IllegalArgumentException("포는 정확히 하나의 기물만 넘을 수 있습니다. 넘은 기물 수: %d".formatted(pieces.size()));
        }
    }

    private void validateNotJumpOverCannon(Set<Piece> pieces) {
        if (hasCannon(pieces)) {
            throw new IllegalArgumentException("포는 포를 넘을 수 없습니다.");
        }
    }

    private boolean hasCannon(Set<Piece> pieces) {
        return pieces.stream()
                .anyMatch(piece -> piece.getPieceSymbol() == this.getPieceSymbol());
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
        return isEnemy(otherPiece)
                && getPieceSymbol() != otherPiece.getPieceSymbol();
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.CANNON;
    }

    @Override
    public int getPoint() {
        return PieceSymbol.CANNON.getPoint();
    }
}
