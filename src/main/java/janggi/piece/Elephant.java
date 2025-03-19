package janggi.piece;

import janggi.Camp;
import janggi.PieceSymbol;
import janggi.Point;
import janggi.board.Board;
import java.util.HashSet;
import java.util.Set;

public final class Elephant extends Piece {

    public Elephant(Camp camp, Board board) {
        super(camp, board);
    }

    @Override
    public void validateMove(Point fromPoint, Point toPoint) {
        if (!((fromPoint.calculateXDistance(toPoint) == 2 && fromPoint.calculateYDistance(toPoint) == 3) ||
                (fromPoint.calculateXDistance(toPoint) == 3 && fromPoint.calculateYDistance(toPoint) == 2))) {
            throw new IllegalArgumentException("상은 직선으로 한 칸, 대각선으로 두 칸 움직여야 합니다.");
        }

        Set<Piece> pieces = getBoard().getPiecesByPoint(findRoute(fromPoint, toPoint));
        if (!pieces.isEmpty()) {
            throw new IllegalArgumentException("상은 기물을 넘어서 이동할 수 없습니다.");
        }
    }

    private Set<Point> findRoute(Point fromPoint, Point toPoint) {
        Set<Point> route = new HashSet<>();
        if (isHorizontal(fromPoint, toPoint)) {
            if (fromPoint.getX() < toPoint.getX()) {
                Point firstPoint = new Point(fromPoint.getX() + 1, fromPoint.getY());
                route.add(firstPoint);
                Point secondPoint = new Point((firstPoint.getX() + toPoint.getX()) / 2,
                        (firstPoint.getY() + toPoint.getY()) / 2);
                route.add(secondPoint);
                return route;
            }
            Point firstPoint = new Point(fromPoint.getX() - 1, fromPoint.getY());
            route.add(firstPoint);
            Point secondPoint = new Point((firstPoint.getX() + toPoint.getX()) / 2,
                    (firstPoint.getY() + toPoint.getY()) / 2);
            route.add(secondPoint);
            return route;
        }
        if (fromPoint.getY() < toPoint.getY()) {
            Point firstPoint = new Point(fromPoint.getX(), fromPoint.getY() + 1);
            route.add(firstPoint);
            Point secondPoint = new Point((firstPoint.getX() + toPoint.getX()) / 2,
                    (firstPoint.getY() + toPoint.getY()) / 2);
            route.add(secondPoint);
            return route;
        }
        Point firstPoint = new Point(fromPoint.getX(), fromPoint.getY() - 1);
        route.add(firstPoint);
        Point secondPoint = new Point((firstPoint.getX() + toPoint.getX()) / 2,
                (firstPoint.getY() + toPoint.getY()) / 2);
        route.add(secondPoint);
        return route;
    }

    private boolean isHorizontal(Point fromPoint, Point toPoint) {
        return fromPoint.calculateXDistance(toPoint) == 3;
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.ELEPHANT;
    }
}
