package domain.piece;

import domain.Country;
import domain.Direction;
import java.util.List;

public class Soldier extends Piece {
    private static final int SOLDIER_DIRECTION_SIZE = 1;

    private static final String INVALID_DIRECTION_SIZE = "[ERROR] 졸・병은 한 칸만 이동할 수 있습니다.";
    private static final String TRY_GO_BACK = "[ERROR] 졸・병은 후진할 수 없습니다.";
    private static final String ONLY_MOVE_STRAIGHT = "[ERROR] 졸・병은 직선으로만 이동 가능합니다.";

    public Soldier(Country country) {
        super(new PieceInfo(PieceType.SOLDIER, country));
    }

    @Override
    public void validateDirections(List<Direction> directions) {
        if (directions.size() != SOLDIER_DIRECTION_SIZE) {
            throw new IllegalArgumentException(INVALID_DIRECTION_SIZE);
        }
        Direction direction = directions.getFirst();
        if (pieceInfo.country() == Country.CHO && direction == Direction.DOWN) {
            throw new IllegalArgumentException(TRY_GO_BACK);
        }
        if (pieceInfo.country() == Country.HAN && direction == Direction.UP) {
            throw new IllegalArgumentException(TRY_GO_BACK);
        }
        // 궁성 영역 생각하지 않음
        if (directions.getFirst().isDialog()) {
            throw new IllegalArgumentException(ONLY_MOVE_STRAIGHT);
        }
    }
}
