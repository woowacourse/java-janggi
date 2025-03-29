package domain.piece;

import domain.board.BoardPosition;
import domain.board.Offset;
import java.util.List;

public class Chariot extends Piece {

    public Chariot(final Team team) {
        super(PieceType.CHARIOT, team);
    }

    @Override
    public List<Offset> findMovementRule(
        final BoardPosition selectedPosition,
        final BoardPosition destinationBoardPosition
    ) {
        final Offset totalOffset = destinationBoardPosition.calculateOffset(selectedPosition);

        if (!totalOffset.isLinear()) {
            throw new IllegalArgumentException("해당 말은 이동할 수 없습니다.");
        }

        return totalOffset.calculateLinearOffsets();
    }
}
