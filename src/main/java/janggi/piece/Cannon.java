package janggi.piece;

import janggi.board.Board;
import janggi.board.point.Point;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

public final class Cannon extends Piece {

    private static final int POSSIBLE_JUMP_OVER_PIECE_COUNT = 1;

    public Cannon(Camp camp, Board board) {
        super(camp, board);
    }

    @Override
    public void validateMove(Point fromPoint, Point toPoint) {
        validateLinearMove(fromPoint, toPoint);
        validateJumpOverOnePiece(fromPoint, toPoint);
    }

    private void validateLinearMove(Point fromPoint, Point toPoint) {
        if (!fromPoint.isHorizontal(toPoint) && !fromPoint.isVertical(toPoint)) {
            throw new IllegalArgumentException("포는 수평 혹은 수직으로만 움직여야 합니다.");
        }
    }

    private void validateJumpOverOnePiece(Point fromPoint, Point toPoint) {
        Set<Piece> pieces = getBoard().getPiecesByPoint(findRoute(fromPoint, toPoint));
        validatePieceCount(pieces);
        validateNotJumpOverCannon(pieces);
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

    private Set<Point> findRoute(Point fromPoint, Point toPoint) {
        boolean isHorizontal = fromPoint.isHorizontal(toPoint);
        if (isHorizontal) {
            return findRouteByFromAndTo(fromPoint.y(), fromPoint.x(), toPoint.x(), Point::new);
        }
        return findRouteByFromAndTo(fromPoint.x(), fromPoint.y(), toPoint.y(), (a, b) -> new Point(b, a));
    }

    private Set<Point> findRouteByFromAndTo(int fixed, int from, int to,
                                            BiFunction<Integer, Integer, Point> pointGenerator) {
        Set<Point> route = new HashSet<>();
        int start = Math.min(from, to) + 1;
        int end = Math.max(from, to);
        for (int i = start; i < end; i++) {
            route.add(pointGenerator.apply(i, fixed));
        }
        return route;
    }

    @Override
    protected boolean canCapture(Piece otherPiece) {
        return getCamp() != otherPiece.getCamp()
                && getPieceSymbol() != otherPiece.getPieceSymbol();
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.CANNON;
    }
}
