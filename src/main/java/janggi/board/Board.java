package janggi.board;

import janggi.piece.Piece;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {

    private static final int COLUMN = 9;
    private static final int ROW = 10;

    private final Map<Point, Piece> placedPieces;

    public Board() {
        this.placedPieces = initializeBoard();
    }

    private Map<Point, Piece> initializeBoard() {
        Map<Point, Piece> board = new HashMap<>();
        for (int i = 1; i <= COLUMN; i++) {
            for (int j = 0; j < ROW; j++) {
                board.put(new Point(i, j), null);
            }
        }
        return board;
    }

    public void placePiece(Point point, Piece piece) {
        validatePoint(point);
        placedPieces.put(point, piece);
    }

    private void validatePoint(Point point) {
        if (!point.isXInRange(0, COLUMN) || !point.isYInRange(0, ROW)) {
            throw new IllegalArgumentException(String.format("기물의 위치는 %d x %d 영역을 벗어날 수 없습니다.", COLUMN, ROW));
        }
    }

    public void move(Point from, Point to) {
        validateMoveRequest(from, to);
        Piece fromPiece = peek(from);
        validateMovable(from, to, fromPiece);
        validatePath(from, to, fromPiece);
        validateCatchable(to, fromPiece);
        executeMove(from, to, fromPiece);
    }

    private void validateMoveRequest(Point from, Point to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException("같은 위치로 이동할 수 없습니다.");
        }
        validatePoint(from);
        validatePoint(to);
    }

    public Piece peek(Point point) {
        Piece piece = placedPieces.get(point);
        if (piece == null) {
            throw new IllegalArgumentException("해당 위치에서 기물을 찾을 수 없습니다.");
        }
        return piece;
    }

    private void validateMovable(Point from, Point to, Piece fromPiece) {
        fromPiece.validateMove(from, to);
    }

    private void validatePath(Point from, Point to, Piece fromPiece) {
        Set<Point> route = fromPiece.findRoute(from, to);
        Set<Piece> piecesByPoint = getPiecesByPoint(route);
        fromPiece.validatePathObstacles(piecesByPoint);
    }

    private void validateCatchable(Point to, Piece fromPiece) {
        Piece toPiece = placedPieces.get(to);
        if (toPiece != null) {
            fromPiece.validateCatch(toPiece);
        }
    }

    private void executeMove(Point from, Point to, Piece fromPiece) {
        placedPieces.put(from, null);
        placedPieces.put(to, fromPiece);
    }

    private Set<Piece> getPiecesByPoint(Set<Point> route) {
        Set<Piece> pieces = new HashSet<>();
        for (Point point : route) {
            Piece piece = placedPieces.get(point);
            if (piece != null) {
                pieces.add(piece);
            }
        }
        return pieces;
    }

    public Map<Point, Piece> getPlacedPieces() {
        return placedPieces;
    }
}
