package movementRule.omniDirectionMover;

import static pieceProperty.PieceType.SA;

import pieceProperty.PieceType;
import pieceProperty.Position;

public final class Sa extends OmniDirectionalMover {

    public Sa(final Position position) {
        super(position);
    }

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
