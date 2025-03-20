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
        validateElephantMove(fromPoint, toPoint);
        validateObstacleOnRoute(fromPoint, toPoint);
    }

    private void validateElephantMove(Point fromPoint, Point toPoint) {
        if (!isElephantMove(fromPoint.calculateXDistance(toPoint), fromPoint.calculateYDistance(toPoint))) {
            throw new IllegalArgumentException("상은 직선으로 한 칸, 대각선으로 두 칸 움직여야 합니다.");
        }
    }

    private boolean isElephantMove(int xDistance, int yDistance) {
        return (xDistance == 2 && yDistance == 3) || (xDistance == 3 && yDistance == 2);
    }

    private void validateObstacleOnRoute(Point fromPoint, Point toPoint) {
        Set<Piece> pieces = getBoard().getPiecesByPoint(findRoute(fromPoint, toPoint));
        if (!pieces.isEmpty()) {
            throw new IllegalArgumentException("상은 기물을 넘어서 이동할 수 없습니다.");
        }
    }

    private Set<Point> findRoute(Point fromPoint, Point toPoint) {
        Set<Point> route = new HashSet<>();
        if (isNextPointOnHorizontal(fromPoint, toPoint)) {
            return findHorizontalRoute(fromPoint, toPoint, route);
        }
        return findVerticalRoute(fromPoint, toPoint, route);
    }

    private boolean isNextPointOnHorizontal(Point fromPoint, Point toPoint) {
        return fromPoint.calculateXDistance(toPoint) == 3;
    }

    private Set<Point> findHorizontalRoute(Point fromPoint, Point toPoint, Set<Point> route) {
        Point firstPoint = getNextHorizontalPoint(fromPoint, toPoint);
        route.add(firstPoint);
        route.add(findSecondPoint(toPoint, firstPoint));
        return route;
    }

    private Set<Point> findVerticalRoute(Point fromPoint, Point toPoint, Set<Point> route) {
        Point firstPoint = getNextVerticalPoint(fromPoint, toPoint);
        route.add(firstPoint);
        route.add(findSecondPoint(toPoint, firstPoint));
        return route;
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

    private Point findSecondPoint(Point toPoint, Point firstPoint) {
        return new Point((firstPoint.getX() + toPoint.getX()) / 2,
                (firstPoint.getY() + toPoint.getY()) / 2);
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.ELEPHANT;
    }
}
