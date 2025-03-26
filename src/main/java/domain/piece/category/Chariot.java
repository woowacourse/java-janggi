package domain.piece.category;

import domain.MoveInfos;
import domain.direction.Directions;
import domain.piece.Piece;
import domain.spatial.Position;

public class Chariot extends Piece {

    private static final int PIECES_TO_PASS = 0;

    public Chariot(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public Chariot updatePosition(final Position position) {
        return new Chariot(position, directions);
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    @Override
    public void validateMove(final MoveInfos moveInfos) {
        if (moveInfos.countPiecesInPath() != PIECES_TO_PASS) {
            throw new IllegalArgumentException("[ERROR] 차는 중간에 기물이 " + PIECES_TO_PASS + "개여야 합니다.");
        }
    }

    @Override
    public void validateMoveByPathPieceCount(final int pathPieceCount) {
        if (pathPieceCount != PIECES_TO_PASS) {
            throw new IllegalArgumentException("[ERROR] 차는 중간에 기물이 " + PIECES_TO_PASS + "개여야 합니다.");
        }
    }
}
