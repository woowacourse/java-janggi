package domain.piece;

import domain.board.Palace;
import exception.ErrorMessage;
import domain.board.Position;

public final class Guard extends SingleStepPiece {
    public Guard(Team team) {
        super(PieceType.GUARD, team);
    }

    @Override
    protected void validateMoveRule(Position from, Position to) {
        super.validateMoveRule(from, to);
        if (!Palace.isInAnyPalace(to)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MOVE_RULE.getMessage());
        }
    }
}
