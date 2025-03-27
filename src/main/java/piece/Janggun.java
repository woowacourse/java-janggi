package piece;

import static pieceProperty.PieceType.JANGGUN;

import pieceProperty.PieceType;
import pieceProperty.Position;

public final class Janggun extends OmniDirectionalMover {

    public Janggun(final Position position) {
        super(position);
    }

    @Override
    public boolean isJanggun() {
        return true;
    }

    @Override
    public boolean isPo() {
        return false;
    }

    @Override
    public PieceType getPieceType() {
        return JANGGUN;
    }

}
