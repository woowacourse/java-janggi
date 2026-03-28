package domain.board;

import domain.Position;
import domain.activePiece.ActivePiece;
import domain.piece.EmptyPiece;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> pieces;

    public Board(Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public void move(Position src, Position dest) {
        Piece piece = pieces.get(src);
        validateCanMove(piece, src, dest);
        List<Position> route = ((ActivePiece) piece).searchRoute(src, dest);
        validateRoute(route);
        applyMove(src, dest, piece);
    }

    private void validateCanMove(Piece piece, Position src, Position dest) {
        if (!piece.canMove(src, dest)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
    }

    private void validateRoute(List<Position> route) {
        for (Position position : route) {
            if (pieceAt(position).isNotEmpty()) {
                throw new IllegalArgumentException("이동 경로에 기물이 있습니다.");
            }
        }
    }

    private void applyMove(Position src, Position dest, Piece piece) {
        pieces.put(dest, piece);
        pieces.put(src, new EmptyPiece());
    }

    public Piece pieceAt(Position position) {
        return pieces.getOrDefault(position, new EmptyPiece());
    }
}