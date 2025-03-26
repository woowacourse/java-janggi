package domain.piece.category;

import domain.MoveInfos;
import domain.direction.Directions;
import domain.piece.Piece;
import domain.spatial.Position;

public class Cannon extends Piece {

    private static final PieceCategory CATEGORY = PieceCategory.CANNON;
    private static final int PIECES_TO_PASS = 1;

    public Cannon(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public PieceCategory getCategory() {
        return CATEGORY;
    }

    @Override
    public Cannon move(final Position position, final MoveInfos moveInfos) {
        validateMove(moveInfos);
        return new Cannon(position, directions);
    }

    private void validateMove(final MoveInfos moveInfos) {
        if (moveInfos.countPiecesInIntermediatePath() != PIECES_TO_PASS) {
            throw new IllegalArgumentException("포는 중간에 기물이 " + PIECES_TO_PASS + "개여야 합니다.");
        }

        if (moveInfos.isSameAsTargetPiece(CATEGORY)) {
            throw new IllegalArgumentException("포는 상대 포를 잡을 수 없습니다.");
        }

        if (moveInfos.hasSamePieceCategoryInPath(CATEGORY)) {
            throw new IllegalArgumentException("포는 다른 포를 지나칠 수 없습니다.");
        }
    }
}
