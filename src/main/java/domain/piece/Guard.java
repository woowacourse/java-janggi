package domain.piece;

import exception.ErrorMessage;
import domain.board.Palace;
import domain.board.Position;

public final class Guard extends SingleStepPiece {
    public Guard(Team team) {
        super(PieceType.GUARD, team);
    }

    @Override
    protected void validateMoveRule(Position from, Position to) {
        super.validateMoveRule(from, to);
        if (!Palace.isInPalace(to)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MOVE_RULE.getMessage());
        }
    }
}
