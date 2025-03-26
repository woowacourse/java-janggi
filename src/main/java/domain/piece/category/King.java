package domain.piece.category;

import domain.MoveInfos;
import domain.direction.Directions;
import domain.piece.Piece;
import domain.spatial.Position;

public class King extends Piece {

    private static final PieceCategory CATEGORY = PieceCategory.KING;

    public King(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public PieceCategory getCategory() {
        return CATEGORY;
    }

    @Override
    public King move(final Position position, final MoveInfos moveInfos) {
        return new King(position, directions);
    }

    @Override
    public boolean isKing() {
        return true;
    }
}
