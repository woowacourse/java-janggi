package janggi.domain;

import janggi.domain.piece.None;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Position;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> pieces;

    public Board(Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public Piece getPieceByPosition(final Position position) {
        return pieces.get(position);
    }

    public Map<Position, Piece> getPieces() {
        return new HashMap<>(pieces);
    }

    public void movePiece(Position beforePosition, Position afterPosition) {
        Piece piece = pieces.get(beforePosition);
        if (None.isNone(piece)) {
            throw new IllegalArgumentException();
        }
        pieces.put(beforePosition, None.createEmpty());
        Piece movedPiece = piece.move(getPieces(), afterPosition);
        pieces.put(afterPosition, movedPiece);
    }
}
