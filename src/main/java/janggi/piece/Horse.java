package janggi.piece;

import janggi.board.point.Point;
import java.util.Set;

public final class Horse extends Piece {

    public Horse(Camp camp) {
        super(camp);
    }

    @Override
    public Set<Point> findRoute(Point fromPoint, Point toPoint) {
        if (isNextPointOnHorizontal(fromPoint, toPoint)) {
            return Set.of(fromPoint.getNextHorizontalStep(toPoint));
        }
        return Set.of(fromPoint.getNextVerticalStep(toPoint));
    }

    @Override
    public void validateMove(Point fromPoint, Point toPoint, Set<Piece> piecesOnRoute) {
        validateHorseMove(fromPoint, toPoint);
        validateObstacleOnRoute(piecesOnRoute);
    }

    private void validateHorseMove(Point fromPoint, Point toPoint) {
        if (!isHorseMove(fromPoint.calculateXDistance(toPoint), fromPoint.calculateYDistance(toPoint))) {
            throw new IllegalArgumentException("마는 직선으로 한 칸, 대각선으로 한 칸 움직여야 합니다.");
        }
    }

    private boolean isHorseMove(int xDistance, int yDistance) {
        return (xDistance == 2 && yDistance == 1) || (xDistance == 1 && yDistance == 2);
    }

    private void validateObstacleOnRoute(Set<Piece> piecesOnRoute) {
        if (!piecesOnRoute.isEmpty()) {
            throw new IllegalArgumentException("마는 기물을 넘어서 이동할 수 없습니다.");
        }
    }

    private boolean isNextPointOnHorizontal(Point fromPoint, Point toPoint) {
        return fromPoint.calculateXDistance(toPoint) == 2;
    }

    @Override
    protected boolean canCapture(Piece otherPiece) {
        return isEnemy(otherPiece);
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.HORSE;
    }

    @Override
    public int getPoint() {
        return PieceSymbol.HORSE.getPoint();
    }
}
