package domain.strategy;

import domain.Position;
import domain.country.CountryType;
import domain.piece.PieceInfos;

public class SoldierMoveStrategy extends OneStepMoveStrategy {
    private static final String TRY_GO_BACK = "[ERROR] 졸・병은 후진할 수 없습니다.";

    @Override
    public void validateToPosition(PieceInfos pathPieceInfos, Position from, Position to) {
        super.validateToPosition(pathPieceInfos, from, to);
        validateSoldierMove(from, to, pathPieceInfos.get(from).countryType());
    }


    public void validateSoldierMove(Position from, Position to, CountryType countryType) {
        if (countryType == CountryType.CHO && from.y() > to.y()) {
            throw new IllegalArgumentException(TRY_GO_BACK);
        }
        if (countryType == CountryType.HAN && from.y() < to.y()) {
            throw new IllegalArgumentException(TRY_GO_BACK);
        }
    }
}
