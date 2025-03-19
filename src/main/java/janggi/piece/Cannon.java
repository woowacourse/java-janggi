package janggi.piece;

import janggi.Camp;
import janggi.PieceSymbol;
import janggi.Point;
import janggi.board.Board;
import java.util.HashSet;
import java.util.Set;

public final class Cannon extends Piece {

    public Cannon(Camp camp, Board board) {
        super(camp, board);
    }

    @Override
    public void validateMove(Point fromPoint, Point toPoint) {
        if (!(fromPoint.isHorizontal(toPoint) || fromPoint.isVertical(toPoint))) {
            throw new IllegalArgumentException("포는 상하좌우로 움직여야 합니다.");
        }
        Set<Piece> pieces = getBoard().getPiecesByPoint(findRoute(fromPoint, toPoint));
        if (pieces.isEmpty()) {
            throw new IllegalArgumentException("포는 하나의 기물을 넘어서만 이동할 수 있습니다.");
        }
        if (pieces.size() != 1) {
            throw new IllegalArgumentException("하나의 기물만 넘을 수 있습니다.");
        }
        boolean hasCannon = pieces.stream()
                .anyMatch(piece -> piece.getPieceSymbol() == this.getPieceSymbol());
        if (hasCannon) {
            throw new IllegalArgumentException("포는 포를 넘을 수 없습니다.");
        }
    }

    private Set<Point> findRoute(Point fromPoint, Point toPoint) {
        if (fromPoint.isHorizontal(toPoint)) {
            return findHorizontalRoute(fromPoint.getY(), fromPoint.getX(), toPoint.getX());
        }
        return findVerticalRoute(fromPoint.getX(), fromPoint.getY(), toPoint.getY());
    }

    private Set<Point> findHorizontalRoute(int y, int fromX, int toX) {
        Set<Point> horizontalRoute = new HashSet<>();
        if (fromX < toX) {
            for (int x = fromX + 1; x < toX; x++) {
                horizontalRoute.add(new Point(x, y));
            }
            return horizontalRoute;
        }
        for (int x = toX + 1; x < fromX; x++) {
            horizontalRoute.add(new Point(x, y));
        }
        return horizontalRoute;
    }

    private Set<Point> findVerticalRoute(int x, int fromY, int toY) {
        Set<Point> verticalRoute = new HashSet<>();
        if (fromY < toY) {
            for (int y = fromY + 1; y < toY; y++) {
                verticalRoute.add(new Point(x, y));
            }
            return verticalRoute;
        }
        for (int y = toY + 1; y < fromY; y++) {
            verticalRoute.add(new Point(x, y));
        }
        return verticalRoute;
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.CANNON;
    }
}
