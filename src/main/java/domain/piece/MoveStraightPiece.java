package domain.piece;

import domain.Direction;
import java.util.List;

public class MoveStraightPiece extends Piece {
    private static final String ONLY_MOVE_STRAIGHT = "[ERROR] 해당 기물은 직선으로만 이동 가능합니다.";
    private static final String FIXED_DIRECTION = "[ERROR] 해당 기물은 하나의 방향으로만 이동 가능합니다.";

    public MoveStraightPiece(PieceInfo pieceInfo) {
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
        // 궁성 영역 생각하지 않음
        if (oneSide.isDiagonal()) {
            throw new IllegalArgumentException(ONLY_MOVE_STRAIGHT);
        }
    }
}
