package domain.piece;

import domain.Country;
import domain.Direction;
import java.util.List;

public class Soldier extends Piece {
    private static final int SOLDIER_DIRECTION_SIZE = 1;

    public Soldier(Country country) {
        super(new PieceInfo(PieceType.SOLDIER, country));
    }

    @Override
    public void validateDirections(List<Direction> directions) {
        if (directions.size() != SOLDIER_DIRECTION_SIZE) {
            throw new IllegalArgumentException("[ERROR] 졸・병은 한 칸만 이동할 수 있습니다.");
        }
        Direction direction = directions.getFirst();
        if (pieceInfo.getCountry() == Country.CHO && direction == Direction.DOWN) {
            throw new IllegalArgumentException("[ERROR] 졸・병은 후진할 수 없습니다.");
        }
        if (pieceInfo.getCountry() == Country.HAN && direction == Direction.UP) {
            throw new IllegalArgumentException("[ERROR] 졸・병은 후진할 수 없습니다.");
        }
        // 궁성 영역 생각하지 않음
        if (directions.getFirst().isDialog()) {
            throw new IllegalArgumentException("[ERROR] 졸・병은 직선으로만 이동 가능합니다.");
        }
    }
}
