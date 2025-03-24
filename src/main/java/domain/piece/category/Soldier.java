package domain.piece.category;

import domain.direction.Directions;
import domain.piece.Piece;
import domain.spatial.Position;

public class Soldier extends Piece {

    private static final int PIECES_TO_PASS = 0;

    public Soldier(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public Soldier updatePosition(final Position position) {
        return new Soldier(position, directions);
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
            throw new IllegalArgumentException("[ERROR] 병/졸은 중간에 기물이 " + PIECES_TO_PASS + "개여야 합니다.");
        }
    }
}
