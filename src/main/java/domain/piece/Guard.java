package domain.piece;

import domain.Country;
import domain.Direction;
import java.util.List;

public class Guard extends Piece {
    private static final int GUARD_DIRECTION_SIZE = 1;

    private static final String INVALID_DIRECTION_SIZE = "[ERROR] 사는 한 칸만 이동할 수 있습니다.";
    private static final String ONLY_MOVE_STRAIGHT = "[ERROR] 사는 직선으로만 이동 가능합니다.";

    public Guard(Country country) {
        super(new PieceInfo(PieceType.GUARD, country));
    }

    @Override
    public void validateDirections(List<Direction> directions) {
        if (directions.size() != GUARD_DIRECTION_SIZE) {
            throw new IllegalArgumentException(INVALID_DIRECTION_SIZE);
        }
        // 궁성 영역 생각하지 않음
        if (directions.getFirst().isDiagonal()) {
            throw new IllegalArgumentException(ONLY_MOVE_STRAIGHT);
        }
    }
}
