package domain.piece.category;

import domain.MoveInfos;
import domain.direction.Directions;
import domain.piece.Piece;
import domain.spatial.Position;

public class Horse extends Piece {

    private static final PieceCategory CATEGORY = PieceCategory.HORSE;
    private static final int PIECES_TO_PASS = 0;

    public Horse(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public PieceCategory getCategory() {
        return CATEGORY;
    }

    @Override
    public Horse move(final Position position, final MoveInfos moveInfos) {
        validateMove(moveInfos);
        return new Horse(position, directions);
    }

    private void validateMove(final MoveInfos moveInfos) {
        if (moveInfos.countPiecesInIntermediatePath() != PIECES_TO_PASS) {
            throw new IllegalArgumentException("마는 중간에 기물이 " + PIECES_TO_PASS + "개여야 합니다.");
        }
    }
}
