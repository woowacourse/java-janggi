package janggi.piece;

import janggi.board.Point;
import janggi.camp.Camp;
import janggi.view.PieceSymbol;
import java.util.Set;

public final class Horse extends Piece {

    private static final int STRAIGHT_STEP = 1;
    private static final int DIAGONAL_STEP = 1;
    private static final int HORSE_MOVE_DISTANCE = STRAIGHT_STEP + DIAGONAL_STEP;

    public Horse(Camp camp) {
        super(camp);
    }

    @Override
    public void validateMove(Point fromPoint, Point toPoint) {
        validateHorseMove(fromPoint, toPoint);
    }

    private void validateHorseMove(Point fromPoint, Point toPoint) {
        if (!isHorseMove(fromPoint.calculateXDistance(toPoint), fromPoint.calculateYDistance(toPoint))) {
            throw new IllegalArgumentException("마는 직선으로 한 칸, 대각선으로 한 칸 움직여야 합니다.");
        }
    }

    private boolean isHorseMove(int xDistance, int yDistance) {
        return (xDistance == HORSE_MOVE_DISTANCE && yDistance == DIAGONAL_STEP)
                || (xDistance == DIAGONAL_STEP && yDistance == HORSE_MOVE_DISTANCE);
    }

    @Override
    public void validatePathObstacles(Set<Piece> piecesOnRoute) {
        if (!piecesOnRoute.isEmpty()) {
            throw new IllegalArgumentException("마는 기물을 넘어서 이동할 수 없습니다.");
        }
    }

    @Override
    public Set<Point> findRoute(Point fromPoint, Point toPoint) {
        if (isNextPointOnHorizontal(fromPoint, toPoint)) {
            return Set.of(getNextHorizontalPoint(fromPoint, toPoint));
        }
        return Set.of(getNextVerticalPoint(fromPoint, toPoint));
    }

    private boolean isNextPointOnHorizontal(Point fromPoint, Point toPoint) {
        return fromPoint.calculateXDistance(toPoint) == HORSE_MOVE_DISTANCE;
    }

    private Point getNextHorizontalPoint(Point fromPoint, Point toPoint) {
        if (fromPoint.getX() < toPoint.getX()) {
            return new Point(fromPoint.getX() + 1, fromPoint.getY());
        }
        return new Point(fromPoint.getX() - 1, fromPoint.getY());
    }

    private Point getNextVerticalPoint(Point fromPoint, Point toPoint) {
        if (fromPoint.getY() < toPoint.getY()) {
            return new Point(fromPoint.getX(), fromPoint.getY() + 1);
        }
        return new Point(fromPoint.getX(), fromPoint.getY() - 1);
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.HORSE;
    }
}
