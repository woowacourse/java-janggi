package domain.piece;

import domain.Country;
import domain.Direction;
import domain.Position;
import java.util.List;

public class Soldier extends MoveOneStepPiece {
    private static final String TRY_GO_BACK = "[ERROR] 졸・병은 후진할 수 없습니다.";

    public Soldier(Country country) {
        super(new PieceInfo(PieceType.SOLDIER, country));
    }

    @Override
    void validateDirections(List<Direction> directions, Position from, Position to) {
        super.validateDirections(directions, from, to);
        if (pieceInfo.country() == Country.CHO && from.y() > to.y()) {
            throw new IllegalArgumentException(TRY_GO_BACK);
        }
        if (pieceInfo.country() == Country.HAN && from.y() < to.y()) {
            throw new IllegalArgumentException(TRY_GO_BACK);
        }
    }
}
