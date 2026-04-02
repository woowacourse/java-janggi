package domain.piece;

import domain.Palace;
import domain.Position;
import domain.state.State;
import java.util.List;
import java.util.Map;

public class MoveInsidePalacePiece extends MoveOneStepPiece {
    private static final String ONLY_MOVE_INSIDE_PALACE = "[ERROR] 해당 기물은 궁성 외부로 이동할 수 없습니다.";

    public MoveInsidePalacePiece(PieceInfo pieceInfo) {
        super(pieceInfo);
    }

    @Override
    public void validateMove(Map<Position, State> pathStates) {
        for (Position position : pathStates.keySet()) {
            validatePosition(position);
        }
        super.validateMove(pathStates);
    }

    private void validatePosition(Position position) {
        Palace palace = Palace.from(this.getPieceCountryType());
        List<Position> positions = palace.getPositions();
        if (!positions.contains(position)) {
            throw new IllegalArgumentException(ONLY_MOVE_INSIDE_PALACE);
        }
    }
}
