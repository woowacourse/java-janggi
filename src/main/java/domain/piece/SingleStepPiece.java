package domain.piece;

import domain.ErrorMessage;
import domain.Offset;

import java.util.List;

public abstract class SingleStepPiece extends Piece {
    public SingleStepPiece(PieceType pieceType, Team team) {
        super(pieceType, team);
    }

    @Override
    public List<Offset> getPathOffset(Offset offset) {
        validateMoveRule(offset);
        return List.of();
    }

    private void validateMoveRule(Offset offset) {
        if (!offset.isSingleStep()) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MOVE_RULE.getMessage());
        }
    }
}
