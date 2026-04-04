package domain.strategy;

import domain.Palace;
import domain.Position;
import domain.country.CountryType;
import domain.piece.PieceInfos;
import java.util.List;

public class InsidePalaceMoveStrategy extends OneStepMoveStrategy {
    private static final String ONLY_MOVE_INSIDE_PALACE = "[ERROR] 해당 기물은 궁성 외부로 이동할 수 없습니다.";

    @Override
    public void validateMove(PieceInfos pieceInfos, Position from, Position to) {
        validateInPalace(from, to, pieceInfos.get(from).countryType());
        super.validateMove(pieceInfos, from, to);
    }

    private void validateInPalace(Position from, Position to, CountryType countryType) {
        Palace palace = Palace.from(countryType);
        List<Position> positions = palace.getPositions();
        if (!positions.contains(from) || !positions.contains(to)) {
            throw new IllegalArgumentException(ONLY_MOVE_INSIDE_PALACE);
        }
    }
}
