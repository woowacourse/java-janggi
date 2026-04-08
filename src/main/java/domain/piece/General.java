package domain.piece;

import domain.board.Palace;
import exception.ErrorMessage;
import domain.board.Position;

public final class General extends SingleStepPiece{
    public General(Team team) {
        super(PieceType.GENERAL, team);
    }

    @Override
    protected void validateMoveRule(Position from, Position to) {
        super.validateMoveRule(from, to);
        if (!Palace.isInAnyPalace(to)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MOVE_RULE.getMessage());
        }
    }
}
