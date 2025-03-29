package movementRule.omniDirectionMover;

import static pieceProperty.PieceType.JANGGUN;

import pieceProperty.PieceType;

public class ChoJanggun extends ChoOmniDirectionalMover{

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
