package janggi.board;

import janggi.board.point.Point;
import janggi.piece.Piece;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class Board {

    private static final int COLUMN = 9;
    private static final int ROW = 10;

    private final Map<Point, Piece> placedPieces;

    public Board() {
        placedPieces = new HashMap<>();
    }

    public void placePiece(Point point, Piece piece) {
        validatePointWithinBounds(point);
        placedPieces.put(point, piece);
    }

    public void movePiece(Point fromPoint, Point toPoint) {
        validateMoveRequest(fromPoint, toPoint);
        Piece fromPiece = getPiece(fromPoint);
        fromPiece.validateMove(fromPoint, toPoint);
        validateCaptureEligibility(fromPiece, toPoint);
        movePieceOnBoard(fromPoint, toPoint, fromPiece);
    }

    private void validateMoveRequest(Point fromPoint, Point toPoint) {
        if (fromPoint.equals(toPoint)) {
            throw new IllegalArgumentException("같은 위치로 이동할 수 없습니다.");
        }
        validatePointWithinBounds(fromPoint);
        validatePointWithinBounds(toPoint);
    }

    private void validatePointWithinBounds(Point point) {
        boolean isOutOfBound = point.x() < 0 || COLUMN <= point.x() || point.y() < 0 || ROW <= point.y();
        if (isOutOfBound) {
            throw new IllegalArgumentException("기물의 위치는 %d x %d 영역을 벗어날 수 없습니다.".formatted(COLUMN, ROW));
        }
    }

    private void validateCaptureEligibility(Piece fromPiece, Point toPoint) {
        if (!placedPieces.containsKey(toPoint)) {
            return;
        }
        fromPiece.validateCatch(placedPieces.get(toPoint));
    }

    private void movePieceOnBoard(Point fromPoint, Point toPoint, Piece fromPiece) {
        placedPieces.remove(fromPoint);
        placedPieces.put(toPoint, fromPiece);
    }

    public Set<Piece> getPiecesByPoint(Set<Point> route) {
        return route.stream()
                .filter(placedPieces::containsKey)
                .map(placedPieces::get)
                .collect(Collectors.toUnmodifiableSet());
    }

    public Piece getPiece(Point point) {
        if (!placedPieces.containsKey(point)) {
            throw new IllegalArgumentException("해당 위치에 기물이 없습니다. 위치: (%s, %s)".formatted(point.x(), point.y()));
        }
        return placedPieces.get(point);
    }

    public Map<Point, Piece> getPlacedPieces() {
        return placedPieces;
    }
}
