package janggi.board;

import janggi.Point;
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
        if (point.getX() < 0 || COLUMN <= point.getX() || point.getY() < 0 || ROW <= point.getY()) {
            throw new IllegalArgumentException("기물의 위치는 9 x 10 영역을 벗어날 수 없습니다.");
        }
    }

    public Map<Point, Piece> getPlacedPieces() {
        return placedPieces;
    }

    public Piece peek(Point point) {
        Piece piece = placedPieces.get(point);
        if (piece == null) {
            throw new IllegalArgumentException("해당 위치에서 기물을 찾을 수 없습니다.");
        }
        return piece;
    }

    public void move(Point from, Point to) {
        validatePoint(from);
        validatePoint(to);
        Piece fromPiece = peek(from);
        fromPiece.validateMove(from, to);
        Piece toPiece = placedPieces.get(to);
        if (toPiece != null) {
            fromPiece.validateCatch(toPiece);
        }
        placedPieces.put(from, null);
        placedPieces.put(to, fromPiece);
    }

    public Set<Piece> getPiecesByPoint(Set<Point> route) {
        Set<Piece> pieces = new HashSet<>();
        for (Point point : route) {
            Piece piece = placedPieces.get(point);
            if (piece != null) {
                pieces.add(piece);
            }
        }
        return pieces;
    }
}
