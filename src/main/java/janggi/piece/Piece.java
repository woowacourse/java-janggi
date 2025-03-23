package janggi.piece;

import janggi.value.JanggiPosition;
import java.util.List;

public abstract class Piece {

    private final PieceType pieceType;
    private final JanggiPosition janggiPosition;

    protected Piece(final PieceType pieceType, final JanggiPosition janggiPosition) {
        this.pieceType = pieceType;
        this.janggiPosition = janggiPosition;
    }

    abstract public Piece move(final JanggiPosition destination, Pieces enemy, Pieces allies);

    abstract protected boolean ableToMove(final JanggiPosition destination, Pieces enemy, Pieces allies);

    public PieceType getPieceType() {
        return pieceType;
    }

    public boolean checkPieceType(PieceType pieceType) {
        return this.pieceType == pieceType;
    }

    public JanggiPosition getPosition() {
        return janggiPosition;
    }
}
