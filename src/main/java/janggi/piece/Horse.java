package janggi.piece;

import janggi.Camp;
import janggi.PieceSymbol;
import janggi.Point;
import janggi.board.Board;
import java.util.Set;

public final class Horse extends Piece {

    public Horse(Camp camp, Board board) {
        super(camp, board);
    }

    @Override
    public void validateMove(Point fromPoint, Point toPoint) {
        if (!((fromPoint.calculateXDistance(toPoint) == 2 && fromPoint.calculateYDistance(toPoint) == 1) ||
                (fromPoint.calculateXDistance(toPoint) == 1 && fromPoint.calculateYDistance(toPoint) == 2))) {
            throw new IllegalArgumentException("마는 직선으로 한 칸, 대각선으로 한 칸 움직여야 합니다.");
        }

        Set<Piece> pieces = getBoard().getPiecesByPoint(Set.of(findRoute(fromPoint, toPoint)));
        if (!pieces.isEmpty()) {
            throw new IllegalArgumentException("마는 기물을 넘어서 이동할 수 없습니다.");
        }
    }

    private Point findRoute(Point fromPoint, Point toPoint) {
        if (isHorizontal(fromPoint, toPoint)) {
            if (fromPoint.getX() < toPoint.getX()) {
                return new Point(fromPoint.getX() + 1, fromPoint.getY());
            }
            return new Point(fromPoint.getX() - 1, fromPoint.getY());
        }
        if (fromPoint.getY() < toPoint.getY()) {
            return new Point(fromPoint.getX(), fromPoint.getY() + 1);
        }
        return new Point(fromPoint.getX(), fromPoint.getY() - 1);
    }

    private boolean isHorizontal(Point fromPoint, Point toPoint) {
        return fromPoint.calculateXDistance(toPoint) == 2;
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.HORSE;
    }
}
