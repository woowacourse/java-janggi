package janggi.domain.path;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;

import java.util.ArrayList;
import java.util.List;

public class PieceOnPath {

    private final List<Piece> pieces;

    public PieceOnPath() {
        this.pieces = new ArrayList<>();
    }

    public void add(Piece piece) {
        pieces.add(piece);
    }

    public long countNonEmpty() {
        return pieces.stream()
                .filter(piece -> !piece.isEmptyPiece())
                .count();
    }

    public boolean hasType(PieceType type) {
        return pieces.stream().anyMatch(piece -> piece.isSameType(type));
    }
}
