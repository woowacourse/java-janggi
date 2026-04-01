package domain.piece;

import domain.Country;
import domain.Direction;
import domain.Position;
import java.util.List;

public class Horse extends Piece {
    private static final int HORSE_DIRECTION_SIZE = 2;

    private static final String INVALID_DIRECTION_SIZE = "[ERROR] 마가 이동할 수 있는 방향은 2개이어야 합니다.";
    private static final String FIXED_DIRECTION = "[ERROR] 마의 1번째 방향은 직선이고, 2번째 방향은 대각선이어야 합니다.";

    public Horse(Country country) {
        super(new PieceInfo(PieceType.HORSE, country));
    }

    @Override
    void validateDirections(List<Direction> directions, Position from, Position to) {
        if (directions.size() != HORSE_DIRECTION_SIZE) {
            throw new IllegalArgumentException(INVALID_DIRECTION_SIZE);
        }
        if (directions.getFirst().isDiagonal() || !directions.get(1).isDiagonal()) {
            throw new IllegalArgumentException(FIXED_DIRECTION);
        }
    }
}
