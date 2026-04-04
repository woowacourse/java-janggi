package domain.piece;

import domain.Offset;

public final class General extends SingleStepPiece{
    public General(Team team) {
        super(PieceType.GENERAL, team);
    }

    @Override
    protected void validateMoveRule(Offset offset) {
        super.validateMoveRule(offset);
    }
}
