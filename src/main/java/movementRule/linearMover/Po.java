package movementRule.linearMover;

import static pieceProperty.PieceType.PO;

import pieceProperty.PieceType;

public final class Po extends LinearMover {

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
