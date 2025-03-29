package movementRule.linearMover;

import static pieceProperty.PieceType.CHA;

import pieceProperty.PieceType;

public final class Cha extends LinearMover {

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
        return CHA;
    }

}
