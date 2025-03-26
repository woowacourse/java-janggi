package domain.piece.category;

import domain.MoveInfos;
import domain.direction.Directions;
import domain.piece.Piece;
import domain.spatial.Position;

public class King extends Piece {

    private static final PieceCategory CATEGORY = PieceCategory.KING;
    private static final int PIECES_TO_PASS = 0;

    public King(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public PieceCategory getCategory() {
        return CATEGORY;
    }

    @Override
    public King move(final Position position, final MoveInfos moveInfos) {
        validateMove(moveInfos);
        return new King(position, directions);
    }

    @Override
    public boolean isKing() {
        return true;
    }

    private void validateMove(final MoveInfos moveInfos) {
        if (moveInfos.countPiecesInPath() != PIECES_TO_PASS) {
            throw new IllegalArgumentException("[ERROR] 왕은 중간에 기물이 " + PIECES_TO_PASS + "개여야 합니다.");
        }
    }
}
