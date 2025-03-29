package movementRule.omniDirectionMover;

import static pieceProperty.PieceType.SA;

import pieceProperty.PieceType;

public final class Sa extends OmniDirectionalMover {

    @Override
    public boolean isPo() {
        return false;
    }

    @Override
    public boolean isJanggun() {
        return false;
    }

    @Override
    public PieceType getPieceType() {
        return SA;
    }

}
