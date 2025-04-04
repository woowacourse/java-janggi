package janggi.domain.piece;

import janggi.domain.value.JanggiPosition;

public abstract class Piece {

    protected final PieceType pieceType;
    protected final JanggiPosition janggiPosition;

    protected Piece(final PieceType pieceType, final JanggiPosition janggiPosition) {
        this.pieceType = pieceType;
        this.janggiPosition = janggiPosition;
    }

    abstract public Piece move(final JanggiPosition destination, Pieces enemy, Pieces allies);

    abstract protected boolean ableToMove(final JanggiPosition destination, Pieces enemy, Pieces allies);

    public PieceType getPieceType() {
        return pieceType;
    }

    public JanggiPosition getPosition() {
        return janggiPosition;
    }
}
