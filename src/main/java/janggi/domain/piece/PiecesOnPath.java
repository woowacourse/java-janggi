package janggi.domain.piece;

import java.util.List;

public class PiecesOnPath {

    private final List<Piece> pieces;

    public PiecesOnPath(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public boolean isAllEmpty() {
        return pieces.stream().allMatch(Piece::isEmptyPiece);
    }

    public boolean hasExactlyOneNonEmpty() {
        return pieces.stream()
                .filter(piece -> !piece.isEmptyPiece())
                .count() == 1;
    }

    public boolean containsType(PieceType type) {
        return pieces.stream().anyMatch(piece -> piece.isSameType(type));
    }
}
