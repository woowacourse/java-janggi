package domain.piece;

import domain.CountryType;
import domain.Direction;
import domain.Position;
import java.util.List;

public class Elephant extends Piece {
    private static final int ELEPHANT_DIRECTION_SIZE = 3;

    private static final String INVALID_DIRECTION_SIZE = "[ERROR] 상이 이동할 수 있는 방향은 3개이어야 합니다.";
    private static final String MUST_SAME_DIRECTION = "[ERROR] 상의 2번째 방향과 3번째 방향은 동일해야 합니다.";
    private static final String FIXED_DIRECTION = "[ERROR] 상의 1번째 방향은 직선이고, 2, 3번째 방향은 대각선이어야 합니다.";

    public Elephant(CountryType countryType) {
        super(new PieceInfo(PieceType.ELEPHANT, countryType));
    }

    @Override
    void validateDirections(List<Direction> directions, Position from, Position to) {
        if (directions.size() != ELEPHANT_DIRECTION_SIZE) {
            throw new IllegalArgumentException(INVALID_DIRECTION_SIZE);
        }
        if (directions.get(1) != directions.get(2)) {
            throw new IllegalArgumentException(MUST_SAME_DIRECTION);
        }
        if (directions.getFirst().isDiagonal() || !directions.get(1).isDiagonal()) {
            throw new IllegalArgumentException(FIXED_DIRECTION);
        }
    }
}
