package domain.piece;

import domain.Country;
import domain.Direction;
import java.util.List;

public class Soldier extends MoveOneStepPiece {
    private static final String TRY_GO_BACK = "[ERROR] 졸・병은 후진할 수 없습니다.";

    public Soldier(Country country) {
        super(new PieceInfo(PieceType.SOLDIER, country));
    }

    @Override
    public void validateDirections(List<Direction> directions) {
        super.validateDirections(directions);
        Direction direction = directions.getFirst();
        if (pieceInfo.country() == Country.CHO && direction == Direction.DOWN) {
            throw new IllegalArgumentException(TRY_GO_BACK);
        }
        if (pieceInfo.country() == Country.HAN && direction == Direction.UP) {
            throw new IllegalArgumentException(TRY_GO_BACK);
        }
    }
}
