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
        this.placedPieces = new HashMap<>();
    }

    public void placePiece(Point point, Piece piece) {
        validatePoint(point);
        placedPieces.put(point, piece);
    }

    private void validatePoint(Point point) {
        if (!point.isXBetween(0, COLUMN) || !point.isYBetween(0, ROW)) {
            throw new IllegalArgumentException(String.format("기물의 위치는 %d x %d 영역을 벗어날 수 없습니다.", COLUMN, ROW));
        }
    }

    public void move(Point from, Point to) {
        validateMoveRequest(from, to);
        Piece movingPiece = peek(from);
        validateMovable(movingPiece, from, to);
        validateRoute(movingPiece, from, to);
        validateCatchable(movingPiece, to);
        executeMove(movingPiece, from, to);
    }

    private void validateMoveRequest(Point from, Point to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException("같은 위치로 이동할 수 없습니다.");
        }
        validatePoint(from);
        validatePoint(to);
    }

    public Piece peek(Point point) {
        if (!placedPieces.containsKey(point)) {
            throw new IllegalArgumentException("해당 위치에서 기물을 찾을 수 없습니다.");
        }
        return placedPieces.get(point);
    }

    private void validateMovable(Piece movingPiece, Point from, Point to) {
        movingPiece.validateMove(from, to);
    }

    private void validateRoute(Piece movingPiece, Point from, Point to) {
        Set<Point> route = movingPiece.findRoute(from, to);
        Set<Piece> piecesByPoint = findPiecesByPoint(route);
        movingPiece.validateRouteObstacles(piecesByPoint);
    }

    private void validateCatchable(Piece movingPiece, Point to) {
        if (placedPieces.containsKey(to)) {
            Piece targetPiece = peek(to);
            movingPiece.validateCatch(targetPiece);
        }
    }

    private void executeMove(Piece movingPiece, Point from, Point to) {
        placedPieces.remove(from);
        placedPieces.put(to, movingPiece);
    }

    private Set<Piece> findPiecesByPoint(Set<Point> route) {
        Set<Piece> pieces = new HashSet<>();
        for (Point point : route) {
            Piece piece = peek(point);
            pieces.add(piece);
        }
        return pieces;
    }

    public Map<Point, Piece> getPlacedPieces() {
        return placedPieces;
    }
}
