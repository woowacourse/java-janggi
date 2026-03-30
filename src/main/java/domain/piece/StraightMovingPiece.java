package domain.piece;

import domain.Direction;
import java.util.List;

public abstract class StraightMovingPiece extends Piece {
    private static final String ONLY_MOVE_STRAIGHT = "[ERROR] 포 또는 차는 직선으로만 이동 가능합니다.";
    private static final String FIXED_DIRECTION = "[ERROR] 포 또는 차는 하나의 방향으로만 이동 가능합니다.";

    public StraightMovingPiece(PieceInfo pieceInfo) {
        super(pieceInfo);
    }

    @Override
    public void validateDirections(List<Direction> directions) {
        Direction oneSide = directions.getFirst();
        boolean allSameDirection = directions.stream()
                .allMatch(direction -> direction.equals(oneSide));
        if (!allSameDirection) {
            throw new IllegalArgumentException(FIXED_DIRECTION);
        }
        if (oneSide.isDiagonal()) {
            throw new IllegalArgumentException(ONLY_MOVE_STRAIGHT);
        }
    }
}
