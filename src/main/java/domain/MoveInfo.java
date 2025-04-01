package domain;

import domain.piece.category.PieceCategory;
import domain.spatial.Position;
import domain.spatial.Vector;

public class MoveInfo {

    private final Position position;
    private final PieceCategory pieceCategory;

    public MoveInfo(final Position position, final PieceCategory pieceCategory) {
        this.position = position;
        this.pieceCategory = pieceCategory;
    }

    public Vector calculateDirection(final MoveInfo other) {
        return this.position.calculateVector(other.position);
    }

    public boolean hasPieceInPath() {
        return pieceCategory != PieceCategory.NONE;
    }

    public boolean isSamePieceCategory(final PieceCategory other) {
        return this.pieceCategory == other;
    }

    public boolean isInsidePalace() {
        return position.isWithinPalace();
    }
}
