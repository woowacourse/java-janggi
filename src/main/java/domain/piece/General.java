package domain.piece;

import domain.board.Palace;
import exception.ErrorMessage;
import domain.board.Position;

import java.util.Optional;

public final class General extends SingleStepPiece{
    public General(Team team) {
        super(PieceType.GENERAL, team);
    }

    @Override
    protected void validateMoveRule(Position from, Position to, Optional<Palace> palace) {
        super.validateMoveRule(from, to, palace);
        if(palace.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MOVE_RULE.getMessage());
        }
        Palace currentPalace = palace.get();
        if (!(currentPalace.isInPalace(from) && currentPalace.isInPalace(to))) {
            throw new IllegalStateException(ErrorMessage.INVALID_MOVE_RULE.getMessage());
        }
    }
}
