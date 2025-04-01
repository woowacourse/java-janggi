package domain;

import domain.piece.category.PieceCategory;
import domain.spatial.Position;

public class MoveInfo {

    private final Position position;
    private final PieceCategory pieceCategory;

    public MoveInfo(final Position position, final PieceCategory pieceCategory) {
        this.position = position;
        this.pieceCategory = pieceCategory;
    }

    public boolean hasPieceInPath() {
        return pieceCategory != PieceCategory.NONE;
    }

    public boolean isSamePieceCategory(final PieceCategory other) {
        return this.pieceCategory == other;
    }
}
