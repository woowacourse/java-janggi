package piece;

import static pieceProperty.PieceType.JANGGUN;

import pieceProperty.PieceType;
import pieceProperty.Position;

public final class Janggun extends OmniDirectionalMover {

    public Janggun(final Position position) {
        super(position);
    }

    @Override
    public PieceType getPieceType() {
        return JANGGUN;
    }

}
