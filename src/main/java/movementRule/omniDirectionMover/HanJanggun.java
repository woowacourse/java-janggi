package movementRule.omniDirectionMover;

import static pieceProperty.PieceType.JANGGUN;

import pieceProperty.PieceType;

public class HanJanggun extends HanOmniDirectionalMover {

    @Override
    public int getScore() {
        return 10;
    }

    @Override
    public boolean isPo() {
        return false;
    }

    @Override
    public boolean isJanggun() {
        return true;
    }

    @Override
    public PieceType getPieceType() {
        return JANGGUN;
    }

}
