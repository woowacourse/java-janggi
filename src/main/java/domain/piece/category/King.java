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
    public King move(final Position target, final MoveInfos moveInfos) {
        validateMoveWithinPalace(target);
        return new King(target, directions);
    }

    @Override
    public boolean isKing() {
        return true;
    }

    private void validateMoveWithinPalace(final Position target) {
        if (!target.isWithinPalace()) {
            throw new IllegalArgumentException("왕은 궁성 밖으로 이동할 수 없습니다.");
        }
    }
}
