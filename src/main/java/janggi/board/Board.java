package janggi.board;

import janggi.Point;
import janggi.piece.EmptySpace;
import janggi.piece.Piece;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class Board {

    static final int COLUMN = 9;
    static final int ROW = 10;

    private final Map<Point, Piece> placedPieces;

    public Board() {
        placedPieces = initializeBoard();
    }

    private Map<Point, Piece> initializeBoard() {
        Map<Point, Piece> pieces = new HashMap<>();
        for (int i = 0; i < ROW; i++) {
            initializeRow(pieces, i);
        }
        return pieces;
    }

    private void initializeRow(Map<Point, Piece> pieces, int i) {
        for (int j = 0; j < COLUMN; j++) {
            pieces.put(new Point(i, j), new EmptySpace(this));
        }
    }

    public void placePiece(Point point, Piece piece) {
        validatePointWithinBounds(point);
        placedPieces.put(point, piece);
    }

    public void movePiece(Point from, Point to) {
        validateMoveRequest(from, to);
        Piece fromPiece = getPiece(from);
        fromPiece.validateMove(from, to);
        validateCaptureEligibility(fromPiece, to);
        movePieceOnBoard(from, to, fromPiece);
    }

    private void validateMoveRequest(Point from, Point to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException("같은 위치로 이동할 수 없습니다.");
        }
        validatePointWithinBounds(from);
        validatePointWithinBounds(to);
    }

    private void validatePointWithinBounds(Point point) {
        boolean isOutOfBound = point.x() < 0 || COLUMN <= point.x() || point.y() < 0 || ROW <= point.y();
        if (isOutOfBound) {
            throw new IllegalArgumentException("기물의 위치는 %d x %d 영역을 벗어날 수 없습니다.".formatted(COLUMN, ROW));
        }
    }

    private void validateCaptureEligibility(Piece fromPiece, Point to) {
        fromPiece.validateCatch(placedPieces.get(to));
    }

    private void movePieceOnBoard(Point from, Point to, Piece fromPiece) {
        placedPieces.put(from, new EmptySpace(this));
        placedPieces.put(to, fromPiece);
    }

    public Set<Piece> getPiecesByPoint(Set<Point> route) {
        return route.stream()
                .map(placedPieces::get)
                .filter(Piece::exists)
                .collect(Collectors.toUnmodifiableSet());
    }

    public Piece getPiece(Point point) {
        return placedPieces.get(point);
    }

    public Map<Point, Piece> getPlacedPieces() {
        return placedPieces;
    }
}
