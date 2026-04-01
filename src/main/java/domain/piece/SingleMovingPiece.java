package domain.piece;

import domain.Direction;
import java.util.List;

public abstract class SingleMovingPiece extends Piece {
    private static final int DIRECTION_SIZE = 1;

    private static final String INVALID_DIRECTION_SIZE = "[ERROR] 한 칸만 이동할 수 있습니다.";
    private static final String ONLY_MOVE_STRAIGHT = "[ERROR] 직선으로만 이동 가능합니다.";

    public SingleMovingPiece(PieceInfo pieceInfo) {
        super(pieceInfo);
    }

    @Override
    protected void validateDirections(List<Direction> directions) {
        if (directions.size() != DIRECTION_SIZE) {
            throw new IllegalArgumentException(INVALID_DIRECTION_SIZE);
        }
        // 궁성 영역 생각하지 않음
        if (directions.getFirst().isDiagonal()) {
            throw new IllegalArgumentException(ONLY_MOVE_STRAIGHT);
        }
    }
}
