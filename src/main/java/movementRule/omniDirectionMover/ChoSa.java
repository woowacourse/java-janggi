package movementRule.omniDirectionMover;

import static pieceProperty.PieceType.SA;

import pieceProperty.PieceType;

public class ChoSa extends  ChoOmniDirectionalMover{

    @Override
    public int getScore() {
        return 1;
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
