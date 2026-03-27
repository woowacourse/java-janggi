package domain.piece;

import domain.Country;
import domain.Direction;
import java.util.List;

public class General extends Piece {
    private static final int GENERAL_DIRECTION_SIZE = 1;

    public General(Country country) {
        super(new PieceInfo(PieceType.GENERAL, country));
    }

    @Override
    public void validateDirections(List<Direction> directions) {
        if (directions.size() != GENERAL_DIRECTION_SIZE) {
            throw new IllegalArgumentException("[ERROR] 궁은 한 칸만 이동할 수 있습니다.");
        }
        // 궁성 영역 생각하지 않음
        if (directions.getFirst().isDialog()) {
            throw new IllegalArgumentException("[ERROR] 궁은 직선으로만 이동 가능합니다.");
        }
    }
}
