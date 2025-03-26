package domain.piece.category;

import domain.MoveInfos;
import domain.direction.Directions;
import domain.piece.Piece;
import domain.spatial.Position;

public class Soldier extends Piece {

    private static final PieceCategory CATEGORY = PieceCategory.SOLDIER;
    private static final int PIECES_TO_PASS = 0;

    public Soldier(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public Soldier move(final Position position, final MoveInfos moveInfos) {
        validateMove(moveInfos);
        return new Soldier(position, directions);
    }

    @Override
    public PieceCategory getCategory() {
        return CATEGORY;
    }

    private void validateMove(final MoveInfos moveInfos) {
        if (moveInfos.countPiecesInPath() != PIECES_TO_PASS) {
            throw new IllegalArgumentException("[ERROR] 병/졸은 중간에 기물이 " + PIECES_TO_PASS + "개여야 합니다.");
        }
    }
}
