package domain.piece;

import domain.board.Country;
import domain.board.Direction;
import java.util.List;

public class Soldier extends SingleMovingPiece {
    private static final String TRY_GO_BACK = "[ERROR] 졸・병은 후진할 수 없습니다.";
    private static final String CHO_ONLY_DIAGONAL_UP = "[ERROR] 초나라 졸・병은 궁성 내부에서 대각선으로 후진할 수 없습니다.";
    private static final String HAN_ONLY_DIAGONAL_UP = "[ERROR] 한나라 졸・병은 궁성 내부에서 대각선으로 후진할 수 없습니다.";

    public Soldier(Country country) {
        super(new PieceInfo(PieceType.SOLDIER, country));
    }

    @Override
    protected void validateDirectionsInPalace(List<Direction> directions) {
        super.validateDirectionsInPalace(directions);

        Direction direction = directions.getFirst();

        if (pieceInfo.country() == Country.CHO && (direction == Direction.LEFT_DOWN
                || direction == Direction.RIGHT_DOWN)) {
            throw new IllegalArgumentException(CHO_ONLY_DIAGONAL_UP);
        }
        if (pieceInfo.country() == Country.HAN && (direction == Direction.LEFT_UP
                || direction == Direction.RIGHT_UP)) {
            throw new IllegalArgumentException(HAN_ONLY_DIAGONAL_UP);
        }
    }

    @Override
    protected void validateDirections(List<Direction> directions) {
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
