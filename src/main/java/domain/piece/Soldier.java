package domain.piece;

import domain.country.CountryType;
import domain.Direction;
import domain.Position;
import java.util.List;

public class Soldier extends MoveOneStepPiece {
    private static final String TRY_GO_BACK = "[ERROR] 졸・병은 후진할 수 없습니다.";

    public Soldier(CountryType countryType) {
        super(new PieceInfo(PieceType.SOLDIER, countryType));
    }

    @Override
    void validateDirections(List<Direction> directions, Position from, Position to) {
        super.validateDirections(directions, from, to);
        if (pieceInfo.countryType() == CountryType.CHO && from.y() > to.y()) {
            throw new IllegalArgumentException(TRY_GO_BACK);
        }
        if (pieceInfo.countryType() == CountryType.HAN && from.y() < to.y()) {
            throw new IllegalArgumentException(TRY_GO_BACK);
        }
    }
}
