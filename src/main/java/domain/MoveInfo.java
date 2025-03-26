package domain;

import domain.piece.category.PieceCategory;
import domain.spatial.Position;

public class MoveInfo {

    private final Position path;
    private final PieceCategory pieceCategory;

    public MoveInfo(final Position path, final PieceCategory pieceCategory) {
        this.path = path;
        this.pieceCategory = pieceCategory;
    }

    public boolean isPieceInPath() {
        return pieceCategory != PieceCategory.NONE;
    }
}
