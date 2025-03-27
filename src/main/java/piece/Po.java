package piece;

import static pieceProperty.PieceType.PO;

import pieceProperty.PieceType;
import pieceProperty.Position;

public final class Po extends LinearMover {

    public Po(final Position position) {
        super(position);
    }

    @Override
    public PieceType getPieceType() {
        return PO;
    }

}
