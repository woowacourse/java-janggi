package janggi.piece;

import janggi.board.Board;
import janggi.board.point.Point;
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
        if (isNextPointOnHorizontal(fromPoint, toPoint)) {
            return findHorizontalRoute(fromPoint, toPoint);
        }
        return findVerticalRoute(fromPoint, toPoint);
    }

    private boolean isNextPointOnHorizontal(Point fromPoint, Point toPoint) {
        return fromPoint.calculateXDistance(toPoint) == 3;
    }

    private Set<Point> findHorizontalRoute(Point fromPoint, Point toPoint) {
        Point firstPoint = fromPoint.getNextHorizontalStep(toPoint);
        Point secondPoint = firstPoint.middlePoint(toPoint);
        return Set.of(firstPoint, secondPoint);
    }

    private Set<Point> findVerticalRoute(Point fromPoint, Point toPoint) {
        Point firstPoint = fromPoint.getNextVerticalStep(toPoint);
        Point secondPoint = firstPoint.middlePoint(toPoint);
        return Set.of(firstPoint, secondPoint);
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.ELEPHANT;
    }

    @Override
    protected boolean canCapture(Piece otherPiece) {
        return getCamp() != otherPiece.getCamp();
    }
}
