package domain.piece.category;

import domain.MoveInfos;
import domain.direction.Directions;
import domain.piece.Piece;
import domain.spatial.Position;

public class Guard extends Piece {

    private static final PieceCategory CATEGORY = PieceCategory.GUARD;

    public Guard(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public PieceCategory getCategory() {
        return CATEGORY;
    }

    @Override
    public Guard move(final Position target, final MoveInfos moveInfos) {
        validateMoveWithinPalace(target);
        return new Guard(target, directions);
    }

    private void validateMoveWithinPalace(final Position target) {
        if (!target.isWithinPalace()) {
            throw new IllegalArgumentException("사는 궁성 밖으로 이동할 수 없습니다.");
        }
    }
}
