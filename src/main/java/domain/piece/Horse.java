package domain.piece;

import domain.Country;
import domain.Direction;
import java.util.List;

public class Horse extends Piece {
    private static final int HORSE_DIRECTION_SIZE = 2;

    public Horse(Country country) {
        super(new PieceInfo(PieceType.HORSE, country));
    }

    @Override
    public void validateDirections(List<Direction> directions) {
        if (directions.size() != HORSE_DIRECTION_SIZE) {
            throw new IllegalArgumentException("[ERROR] 마가 이동할 수 있는 방향은 2개이어야 합니다.");
        }
        if (directions.getFirst().isDialog() || !directions.get(1).isDialog()) {
            throw new IllegalArgumentException("[ERROR] 마의 1번째 방향은 직선이고, 2번째 방향은 대각선이어야 합니다.");
        }
    }
}
