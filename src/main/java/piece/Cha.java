package piece;

import static pieceProperty.PieceType.CHA;

import pieceProperty.PieceType;
import pieceProperty.Position;

public final class Cha extends LinearMover {

    public Cha(final Position position) {
        super(position);
    }

    @Override
    public PieceType getPieceType() {
        return CHA;
    }

}
