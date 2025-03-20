package janggi.piece;

import janggi.Camp;
import janggi.PieceSymbol;
import janggi.Point;
import janggi.board.Board;
import java.util.HashSet;
import java.util.Set;

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
            return findHorizontalRoute(fromPoint.getY(), fromPoint.getX(), toPoint.getX());
        }
        return findVerticalRoute(fromPoint.getX(), fromPoint.getY(), toPoint.getY());
    }

    private Set<Point> findHorizontalRoute(int fixedY, int fromX, int toX) {
        Set<Point> route = new HashSet<>();
        int start = Math.min(fromX, toX) + 1;
        int end = Math.max(fromX, toX);
        for (int i = start; i < end; i++) {
            route.add(new Point(i, fixedY));
        }
        return route;
    }

    private Set<Point> findVerticalRoute(int fixedX, int fromY, int toY) {
        Set<Point> route = new HashSet<>();
        int start = Math.min(fromY, toY) + 1;
        int end = Math.max(fromY, toY);
        for (int i = start; i < end; i++) {
            route.add(new Point(fixedX, i));
        }
        return route;
    }

    @Override
    public void validateCatch(Piece otherPiece) {
        super.validateCatch(otherPiece);
        if (getPieceSymbol() == otherPiece.getPieceSymbol()) {
            throw new IllegalArgumentException("포는 포를 잡을 수 없습니다.");
        }
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.CANNON;
    }
}
