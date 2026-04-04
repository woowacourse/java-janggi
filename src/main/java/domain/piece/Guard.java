package domain.piece;

import domain.Offset;

public final class Guard extends SingleStepPiece {
    public Guard(Team team) {
        super(PieceType.GUARD, team);
    }

    @Override
    protected void validateMoveRule(Offset offset) {
        super.validateMoveRule(offset);
    }
}
