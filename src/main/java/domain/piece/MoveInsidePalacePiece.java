package domain.piece;

import domain.Palace;
import domain.Position;
import java.util.List;

public class MoveInsidePalacePiece extends MoveOneStepPiece {
    private static final String ONLY_MOVE_INSIDE_PALACE = "[ERROR] 해당 기물은 궁성 외부로 이동할 수 없습니다.";

    public MoveInsidePalacePiece(PieceInfo pieceInfo) {
        super(pieceInfo);
    }

    @Override
    public void validateMove(PieceInfos pieceInfos, Position from, Position to) {
        validateInPalace(from, to);
        super.validateMove(pieceInfos, from, to);
    }

    private void validateInPalace(Position from, Position to) {
        Palace palace = Palace.from(this.getPieceCountryType());
        List<Position> positions = palace.getPositions();
        if (!positions.contains(from) || !positions.contains(to)) {
            throw new IllegalArgumentException(ONLY_MOVE_INSIDE_PALACE);
        }
    }
}
