package janggi.board;

import janggi.exception.ErrorException;
import janggi.piece.Empty;
import janggi.piece.Piece;
import janggi.position.Position;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {

    public static final int COLUMN = 9;
    public static final int ROW = 10;

    private final Map<Position, Piece> placedPieces;

    public Board() {
        this.placedPieces = initializeBoard();
    }

    private Map<Position, Piece> initializeBoard() {
        Map<Position, Piece> board = new HashMap<>();
        for (int i = 0; i < COLUMN; i++) {
            for (int j = 0; j < ROW; j++) {
                board.put(new Position(i, j), Empty.INSTANCE);
            }
        }
        return board;
    }

    public void placePiece(Position position, Piece piece) {
        validatePoint(position);
        placedPieces.put(position, piece);
    }

    private void validatePoint(Position position) {
        if (position.getX() < 0 || COLUMN <= position.getX() || position.getY() < 0 || ROW <= position.getY()) {
            throw new ErrorException("기물의 위치는 9 x 10 영역을 벗어날 수 없습니다.");
        }
    }

    public Piece peek(Position position) {
        Piece piece = placedPieces.get(position);
        if (piece.isEmpty()) {
            throw new ErrorException("해당 위치에서 기물을 찾을 수 없습니다.");
        }
        return piece;
    }

    public void move(Position from, Position to) {
        validateMoveRequest(from, to);
        Piece fromPiece = peek(from);
        fromPiece.validateMove(from, to);
        Piece toPiece = placedPieces.get(to);
        if (!toPiece.isEmpty()) {
            fromPiece.validateCatch(toPiece);
        }
        placedPieces.put(from, Empty.INSTANCE);
        placedPieces.put(to, fromPiece);
    }

    private void validateMoveRequest(Position from, Position to) {
        if (from.equals(to)) {
            throw new ErrorException("같은 위치로 이동할 수 없습니다.");
        }
        validatePoint(from);
        validatePoint(to);
    }

    public Set<Piece> getPiecesByPoint(Set<Position> route) {
        Set<Piece> pieces = new HashSet<>();
        for (Position position : route) {
            Piece piece = placedPieces.get(position);
            if (!piece.isEmpty()) {
                pieces.add(piece);
            }
        }
        return pieces;
    }

    public Map<Position, Piece> getPlacedPieces() {
        return placedPieces;
    }
}
