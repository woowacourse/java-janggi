package domain.piece.category;

import domain.direction.Directions;
import domain.piece.Piece;
import domain.spatial.Position;

public class Guard extends Piece {

    private static final int PIECES_TO_PASS = 0;

    public Guard(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public Guard updatePosition(final Position position) {
        return new Guard(position, directions);
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
    public void validateMoveByPathPieceCount(final int pathPieceCount) {
        if (pathPieceCount != PIECES_TO_PASS) {
            throw new IllegalArgumentException("[ERROR] 사는 중간에 기물이 " + PIECES_TO_PASS + "개여야 합니다.");
        }
    }
}
