package movementRule.linearMover;

import static pieceProperty.PieceType.PO;

import pieceProperty.PieceType;
import pieceProperty.Position;

public final class Po extends LinearMover {

    public Po(final Position position) {
        super(position);
    }

    @Override
    public boolean isPo() {
        return true;
    }

    @Override
    public boolean isJanggun() {
        return false;
    }

    @Override
    public PieceType getPieceType() {
        return PO;
    }

}
