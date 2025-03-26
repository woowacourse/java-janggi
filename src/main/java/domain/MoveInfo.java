package domain;

import domain.piece.category.PieceCategory;

public class MoveInfo {

    private final PieceCategory pieceCategory;

    public MoveInfo(final PieceCategory pieceCategory) {
        this.pieceCategory = pieceCategory;
    }

    public boolean hasPieceInPath() {
        return pieceCategory != PieceCategory.NONE;
    }

    public boolean isSamePieceCategory(final PieceCategory otherPieceCategory) {
        return this.pieceCategory == otherPieceCategory;
    }
}
