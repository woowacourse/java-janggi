package piece;

import static pieceProperty.PieceType.CHA;

import pieceProperty.PieceType;
import pieceProperty.Position;

public final class Cha extends LinearMover {

    public Cha(final Position position) {
        super(position);
    }

    @Override
    public boolean isJanggun() {
        return false;
    }

    @Override
    public boolean isPo() {
        return false;
    }

    @Override
    public PieceType getPieceType() {
        return CHA;
    }

}
