package domain.piece;

import domain.Country;
import domain.Direction;
import java.util.List;

public class Cannon extends Piece {
    private static final String ONLY_MOVE_STRAIGHT = "[ERROR] 포는 직선으로만 이동 가능합니다.";
    private static final String FIXED_DIRECTION = "[ERROR] 포는 하나의 방향으로만 이동 가능합니다.";

    public Cannon(Country country) {
        super(new PieceInfo(PieceType.CANNON, country));
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
