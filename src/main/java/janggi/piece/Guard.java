package janggi.piece;

import janggi.board.point.Point;
import java.util.Set;

public final class Guard extends PalaceRestrictedPiece {

    private static final Set<Point> EMPTY_ROUTE = Set.of();

    public Guard(Camp camp) {
        super(camp);
    }

    @Override
    public Set<Point> findRoute(Point fromPoint, Point toPoint) {
        return EMPTY_ROUTE;
    }

    @Override
    protected void validatePalaceRestrictedMove(Point fromPoint, Point toPoint) {
        if (!(isDiagonalPalaceMove(fromPoint, toPoint) && fromPoint.isOneDiagonalStepAway(toPoint))
                && !fromPoint.isOneStepAway(toPoint)) {
            throw new IllegalArgumentException("사는 직선 또는 대각선 한 칸만 이동할 수 있습니다.");
        }
    }

    @Override
    protected void validateObstacleOnRoute(Set<Piece> piecesOnRoute) {
    }

    @Override
    protected boolean canCapture(Piece otherPiece) {
        return isEnemy(otherPiece);
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.GUARD;
    }

    @Override
    public int getPoint() {
        return PieceSymbol.GUARD.getPoint();
    }
}
