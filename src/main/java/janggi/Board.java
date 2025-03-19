package janggi;

import java.util.HashMap;
import java.util.Map;
import janggi.piece.Piece;
import janggi.piece.Position;

public class Board {
    private final Map<Position, Piece> pieces;

    public Board(Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public Map<Position, Piece> getPieces() {
        return pieces;
    }

    public void putPiece(Position position, Piece piece) {
        pieces.put(position, piece);
    }

    public Piece getPiece(Position position) {
        return pieces.getOrDefault(position, Piece.createEmpty());
    }
}
