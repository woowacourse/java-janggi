package janggi.board;

import janggi.Point;
import janggi.piece.Piece;
import java.util.HashMap;
import java.util.Map;

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
}
